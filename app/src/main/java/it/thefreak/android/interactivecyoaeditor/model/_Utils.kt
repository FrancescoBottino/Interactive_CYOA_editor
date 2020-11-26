package it.thefreak.android.interactivecyoaeditor.model

import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.primaryConstructor
import kotlin.reflect.jvm.jvmErasure

fun <T: IdentifiableItem> T.assignNewId(idManager: IdManager): T {
    return this.apply {
        id = idManager.addWithNewId(this)
    }
}

fun <T: IdentifiableItem> T.removeFromIdMap(idManager: IdManager): T {
    return this.apply {
        idManager.remove(this)
    }
}

inline fun <reified T:Item> T.deepCopyItem(idManager: IdManager): T = this.deepCopyItem(T::class, idManager)
fun <T:Item> T.deepCopyItem(type: KClass<T>, idManager: IdManager): T {

    fun <L: Item> ArrayList<*>.deepCopyItemList(type: KClass<L>, idManager: IdManager): ArrayList<L> {
        val newList = ArrayList<L>()
        this.forEach {
            newList.add((it as L).deepCopyItem(type, idManager))
        }
        return newList
    }

    return this::class.primaryConstructor!!.let { primaryConstructor ->
        val classFields = type.declaredMemberProperties
        val newObject = primaryConstructor.parameters
            .map { parameter ->
                val parameterName = parameter.name
                val field = classFields.first {
                    it.name == parameterName
                }
                val fieldValue = field.get(this)
                val fieldType = field.returnType.jvmErasure

                when {
                    fieldValue == null -> parameter to null
                    fieldType.isSubclassOf(ArrayList::class) -> {
                        val listItemType = field.returnType.arguments[0].type!!.jvmErasure
                        if(listItemType.isSubclassOf(Item::class)) {
                            val copiedList = (fieldValue as ArrayList<*>).deepCopyItemList(listItemType as KClass<out Item>, idManager)
                            parameter to copiedList
                        } else {
                            throw Exception("Unknown parameter type ArrayList<${listItemType}>")
                        }
                    }
                    fieldType.isSubclassOf(Item::class) -> {
                        if( field.findAnnotation<ItemLinkField>() == null ) {
                            fun <I: Item> cast_andDeepCopy(value: Any, type: KClass<I>): I {
                                return (value as I).deepCopyItem(type, idManager)
                            }

                            parameter to cast_andDeepCopy(fieldValue, fieldType as KClass<out Item>)
                        } else {
                            parameter to fieldValue
                        }
                    }
                    else -> when(fieldValue) {
                        is String -> parameter to String(fieldValue.toCharArray())
                        is Int -> parameter to fieldValue
                        is Long -> parameter to fieldValue
                        is Short -> parameter to fieldValue
                        is Byte -> parameter to fieldValue
                        is Boolean -> parameter to fieldValue
                        is Double -> parameter to fieldValue
                        is Float -> parameter to fieldValue
                        is Char -> parameter to fieldValue
                        is Enum<*> -> parameter to fieldValue
                        else -> throw Exception("Unknown parameter type (${fieldValue::class})")
                    }
                }
            }
            .toMap()
            .let(primaryConstructor::callBy)

        if(newObject is IdentifiableItem) {
            newObject.assignNewId(idManager)
        }

        newObject
    }
}

inline fun <reified T:Item> T.deepRegisterItem(idManager: IdManager) = this.deepRegisterItem(T::class, idManager)
fun <T:Item> T.deepRegisterItem(type: KClass<T>, idManager: IdManager) {

    fun <L: Item> ArrayList<*>.deepRegisterItemList(type: KClass<L>, idManager: IdManager) {
        this.forEach {
            (it as L).deepRegisterItem(type, idManager)
        }
    }

    if(this is IdentifiableItem) {
        idManager.addWithCurrentId(this, false)
    }
    type.declaredMemberProperties.forEach { field ->
        val fieldValue = field.get(this)
        val fieldType = field.returnType.jvmErasure
        if(fieldValue != null) {
            if(fieldType.isSubclassOf(Item::class)) {
                fun <I: Item> cast(value: Any, type: KClass<I>): I {
                    return value as I
                }

                cast(fieldValue, fieldType as KClass<out Item>).deepRegisterItem(idManager)
            } else if(field.returnType.jvmErasure.isSubclassOf(ArrayList::class)) {
                val listItemType = field.returnType.arguments[0].type!!.jvmErasure
                if(listItemType.isSubclassOf(Item::class)) {
                    (fieldValue as ArrayList<*>).deepRegisterItemList(listItemType as KClass<out Item>, idManager)
                } else {
                    throw Exception("Unknown parameter type ArrayList<${listItemType}>")
                }
            }
        }
    }
}

inline fun <reified T:Item> T.deepLinkItem(idManager: IdManager) = this.deepLinkItem(T::class, idManager)
fun <T:Item> T.deepLinkItem(type: KClass<T>, idManager: IdManager) {

    fun <L: Item> ArrayList<*>.deepLinkItemList(type: KClass<L>, idManager: IdManager) {
        this.forEach {
            (it as L).deepLinkItem(type, idManager)
        }
    }

    fun <K: IdentifiableItem> setLink(field: KProperty1<T, *>, idManager: IdManager, id: String, type: KClass<K>) {
        val item = (idManager.idMap[id] as K?)
                ?: throw Exception("Can't find id ${id}")
        (field as KMutableProperty1<T, K?>).set(this, item)
    }

    type.declaredMemberProperties
        .filter { field -> field.findAnnotation<ItemLinkField>() != null }
        .forEach { field ->
            //IS TO BE LINKED
            val idFieldName = field.name+"Id"
            val idField = type.declaredMemberProperties
                    .firstOrNull { idField ->
                        idField.name == idFieldName
                    }
                    ?: throw Exception("No id field to link from field ${field.name}")
            (idField.get(this) as String?)?.let { id ->
                setLink(field, idManager, id, field.returnType.jvmErasure as KClass<out IdentifiableItem>)
            }
        }
    type.declaredMemberProperties
        .filter { field ->
            field.returnType.jvmErasure.isSubclassOf(ArrayList::class)
        }
        .forEach { field ->
            //HAS CHILD OBJECTS TO LINK
            val value = field.get(this)
            if(value != null) {
                val listItemType = field.returnType.arguments[0].type!!.jvmErasure
                if(listItemType.isSubclassOf(Item::class)) {
                    (value as ArrayList<*>).deepLinkItemList(listItemType as KClass<out Item>, idManager)
                } else {
                    throw Exception("Unknown parameter type ArrayList<${listItemType}>")
                }
            }
        }
}

inline fun <reified T:Item> T.deepDeleteItem(idManager: IdManager, container: ArrayList<T>?) = this.deepDeleteItem(T::class, idManager, container)
fun <T: Item> T.deepDeleteItem(type: KClass<T>, idManager: IdManager, container: ArrayList<T>?) {

    fun <L: Item> ArrayList<*>.deepDeleteItemList(type: KClass<L>, idManager: IdManager) {
        this.forEach {
            (it as L).deepDeleteItem(type, idManager, null)
        }
        this.clear()
    }

    if(this is IdentifiableItem) {
        this.removeFromIdMap(idManager)
    }
    type.declaredMemberProperties.forEach { field ->
        val fieldValue = field.get(this)
        val fieldType = field.returnType.jvmErasure
        if(fieldValue != null) {
            if(fieldType.isSubclassOf(Item::class)) {
                if( field.findAnnotation<ItemLinkField>() == null ) {
                    fun <I: Item> cast(value: Any, type: KClass<I>): I {
                        return value as I
                    }

                    cast(fieldValue, fieldType as KClass<out Item>).deepDeleteItem(idManager, null)
                }
            } else if(field.returnType.jvmErasure.isSubclassOf(ArrayList::class)) {
                val listItemType = field.returnType.arguments[0].type!!.jvmErasure
                if(listItemType.isSubclassOf(Item::class)) {
                    (fieldValue as ArrayList<*>).deepDeleteItemList(listItemType as KClass<out Item>, idManager)
                } else {
                    throw Exception("Unknown parameter type ArrayList<${listItemType}>")
                }
            }
        }
    }
    container?.remove(this)
}

fun <T> KMutableProperty0<ArrayList<T>?>.init(): ArrayList<T> {
    return this.let {
        var arr = this.get()
        if(arr == null) {
            arr = ArrayList()
            it.set(arr)
        }
        arr
    }
}

@Suppress("UNCHECKED_CAST")
fun <T: IdentifiableItem> KMutableProperty0<T?>.getLinkedField(idManager: IdManager, itemIdField: KMutableProperty0<String?>): T? {

    val annotations = this.annotations

    if (this.findAnnotation<ItemLinkField>() == null) {
        error("Assertion failed, tried to get linked item of non linkable field ${this.name}")
    }
    return this.get()
            ?: itemIdField.get()?.let { id ->
                idManager.idMap[id] as T?
            }?.also { item ->
                this.set(item)
            }
}

