package it.thefreak.android.interactivecyoaeditor.model

import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.full.declaredMemberProperties
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
    return this::class.primaryConstructor!!.let { primaryConstructor ->
        val classFields = type.declaredMemberProperties
        val newObject = primaryConstructor.parameters
            .map { parameter ->
                val parameterName = parameter.name
                val field = classFields.first { it.name == parameterName }
                val value = field.get(this)

                when {
                    value == null -> parameter to null
                    field.returnType.jvmErasure.isSubclassOf(ArrayList::class) -> {
                        val listItemType = field.returnType.arguments[0].type!!.jvmErasure
                        if(listItemType.isSubclassOf(Item::class)) {
                            val copiedList = (value as ArrayList<*>).deepCopyItemList(listItemType as KClass<out Item>, idManager)
                            parameter to copiedList
                        } else {
                            throw Exception("Unknown parameter type ArrayList<${listItemType}>")
                        }
                    }
                    else -> when(value) {
                        is Item -> parameter to value.deepCopyItem(idManager)
                        is String -> parameter to String(value.toCharArray())
                        is Int, Long, Short, Byte, Boolean, Double, Float, Char -> parameter to value
                        is Enum<*> -> parameter to value
                        else -> throw Exception("Unknown parameter type (${value::class})")
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
fun <L: Item> ArrayList<*>.deepCopyItemList(type: KClass<L>, idManager: IdManager): ArrayList<L> {
    val newList = ArrayList<L>()
    this.forEach {
        newList.add((it as L).deepCopyItem(type, idManager))
    }
    return newList
}

inline fun <reified T:Item> T.deepRegisterItem(idManager: IdManager) = this.deepRegisterItem(T::class, idManager)
fun <T:Item> T.deepRegisterItem(type: KClass<T>, idManager: IdManager) {
    if(this is IdentifiableItem) {
        idManager.addWithCurrentId(this)
    }
    type.declaredMemberProperties.forEach { field ->
        val value = field.get(this)
        if(value != null) {
            if(value is Item) {
                value.deepRegisterItem(idManager)
            } else if(field.returnType.jvmErasure.isSubclassOf(ArrayList::class)) {
                val listItemType = field.returnType.arguments[0].type!!.jvmErasure
                if(listItemType.isSubclassOf(Item::class)) {
                    (value as ArrayList<*>).deepRegisterItemList(listItemType as KClass<out Item>, idManager)
                } else {
                    throw Exception("Unknown parameter type ArrayList<${listItemType}>")
                }
            }
        }
    }
}
fun <L: Item> ArrayList<*>.deepRegisterItemList(type: KClass<L>, idManager: IdManager) {
    this.forEach {
        (it as L).deepRegisterItem(type, idManager)
    }
}

inline fun <reified T:Item> T.deepDeleteItem(idManager: IdManager, container: ArrayList<T>?) = this.deepDeleteItem(T::class, idManager, container)
fun <T: Item> T.deepDeleteItem(type: KClass<T>, idManager: IdManager, container: ArrayList<T>?) {
    if(this is IdentifiableItem) {
        this.removeFromIdMap(idManager)
    }
    type.declaredMemberProperties.forEach { field ->
        val value = field.get(this)
        if(value != null) {
            if(value is Item) {
                value.deepDeleteItem(idManager, null)
            } else if(field.returnType.jvmErasure.isSubclassOf(ArrayList::class)) {
                val listItemType = field.returnType.arguments[0].type!!.jvmErasure
                if(listItemType.isSubclassOf(Item::class)) {
                    (value as ArrayList<*>).deepDeleteItemList(listItemType as KClass<out Item>, idManager)
                } else {
                    throw Exception("Unknown parameter type ArrayList<${listItemType}>")
                }
            }
        }
    }
    container?.remove(this)
}
fun <L: Item> ArrayList<*>.deepDeleteItemList(type: KClass<L>, idManager: IdManager) {
    this.forEach {
        (it as L).deepDeleteItem(type, idManager, null)
    }
    this.clear()
}

inline fun <reified T:Item> T.deepDeleteItem(idManager: IdManager, container: KMutableProperty0<ArrayList<T>?>) = this.deepDeleteItem(T::class, idManager, container)
fun <T: Item> T.deepDeleteItem(type: KClass<T>, idManager: IdManager, container: KMutableProperty0<ArrayList<T>?>) {
    if(this is IdentifiableItem) {
        this.removeFromIdMap(idManager)
    }
    type.declaredMemberProperties.forEach { field ->
        val value = field.get(this)
        if(value != null) {
            if(value is Item) {
                value.deepDeleteItem(idManager, null)
            } else if(field.returnType.jvmErasure.isSubclassOf(ArrayList::class)) {
                val listItemType = field.returnType.arguments[0].type!!.jvmErasure
                if(listItemType.isSubclassOf(Item::class)) {
                    (field as KMutableProperty0<ArrayList<*>>).deepDeleteItemList(listItemType as KClass<out Item>, idManager)
                } else {
                    throw Exception("Unknown parameter type ArrayList<${listItemType}>")
                }
            }
        }
    }
    container.init().remove(this)
}
fun <L: Item> KMutableProperty0<ArrayList<*>>.deepDeleteItemList(type: KClass<L>, idManager: IdManager) {
    this.get()?.let { list ->
        list.forEach {
            (it as L).deepDeleteItem(type, idManager, this as KMutableProperty0<ArrayList<L>?>)
        }
        list.clear()
    }
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

