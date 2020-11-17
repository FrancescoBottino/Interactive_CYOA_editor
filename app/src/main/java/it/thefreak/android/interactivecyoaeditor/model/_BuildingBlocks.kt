package it.thefreak.android.interactivecyoaeditor.model

import it.thefreak.android.interactivecyoaeditor.init
import it.thefreak.android.interactivecyoaeditor.ui.editor.components.IdManager
import kotlin.reflect.KMutableProperty0

inline fun <reified T: IdentifiableItem> ArrayList<T>.deepCopy(otherList: ArrayList<T>, idManager: IdManager, registerChildIds: Boolean = true) {
    addAll(
            otherList.map{ otherItem ->
                otherItem.newInstance().apply {
                    deepCopy(otherItem, idManager)
                    if(registerChildIds) {
                        insertIntoIdMap(idManager)
                    }
                } as T
            }
    )
}

inline fun <reified T: IdentifiableItem> deepCopyList(otherList: ArrayList<T>?, idManager: IdManager, registerChildIds: Boolean = true): ArrayList<T>? {
    return otherList?.let { otherListNotNull ->
        ArrayList<T>().apply {
            deepCopy(otherListNotNull, idManager, registerChildIds)
        }
    }
}

fun <T: IdentifiableItem> T.insertIntoIdMap(idManager: IdManager): T {
    return this.apply {
        id = idManager.add(this)
    }
}

fun <T: IdentifiableItem> T.insertIntoContainer(container: KMutableProperty0<ArrayList<T>?>): T {
    container.init().add(this)
    return this
}

fun <T: IdentifiableItem> T.register(idManager: IdManager, container: KMutableProperty0<ArrayList<T>?>): T {
    return this.apply {
        insertIntoIdMap(idManager)
        insertIntoContainer(container)
    }
}

fun <T: IdentifiableItem> T.unregister(idManager: IdManager, container: KMutableProperty0<ArrayList<T>?>) {
    idManager.remove(this)
    container.init().remove(this)
}

abstract class IdentifiableItem {
    var ordinal: Int? = null
    var id: String? = null

    open fun deepCopy(other: Any, idManager: IdManager) {
        if( other is IdentifiableItem ) {
            this.id = other.id
            this.insertIntoIdMap(idManager)
        } else {
            throw Exception()
        }
    }

    abstract fun newInstance(): IdentifiableItem
}

abstract class NameableItem: IdentifiableItem() {
    var name: String? = null
    var description: String? = null

    override fun deepCopy(other: Any, idManager: IdManager) {
        super.deepCopy(other, idManager)

        if( other is NameableItem ) {
            this.name = other.name
            this.description = other.description
        } else {
            throw Exception()
        }
    }
}

abstract class NarrativeItem: NameableItem() {
    var image: String? = null
    var icon: String? = null
    var style: Style? = null

    //todo xml
}

abstract class AdventureItem: NarrativeItem() {
    var hide: Boolean? = null
    var requirements: ArrayList<RequirementTree>? = null

    override fun deepCopy(other: Any, idManager: IdManager) {
        super.deepCopy(other, idManager)

        if( other is AdventureItem ) {
            this.ordinal = other.ordinal
            this.hide = other.hide
            this.requirements = deepCopyList(other.requirements, idManager)
        } else {
            throw Exception()
        }
    }
}