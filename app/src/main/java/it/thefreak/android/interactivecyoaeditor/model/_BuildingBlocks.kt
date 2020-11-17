package it.thefreak.android.interactivecyoaeditor.model

import android.graphics.drawable.DrawableContainer
import it.thefreak.android.interactivecyoaeditor.init
import it.thefreak.android.interactivecyoaeditor.ui.editor.components.IdManager
import kotlin.reflect.KMutableProperty0

inline fun <reified T: DeepCopyable> ArrayList<T>.deepCopy(otherList: ArrayList<T>) {
    addAll(
        otherList.map{ otherItem ->
            otherItem.newInstance().apply {
                deepCopy(otherItem)
            } as T
        }
    )
}

inline fun <reified T: DeepCopyable> deepCopyList(otherList: ArrayList<T>?): ArrayList<T>? {
    return otherList?.let { otherListNotNull ->
        ArrayList<T>().apply {
            deepCopy(otherListNotNull)
        }
    }
}

inline fun <reified T: IdentifiableItem> T.insert(idManager: IdManager, container: ArrayList<T>): T {
    this.id = idManager.add(this)
    container.add(this)
    return this
}

inline fun <reified T: IdentifiableItem> T.insert(idManager: IdManager, container: KMutableProperty0<ArrayList<T>?>): T {
    this.id = idManager.add(this)
    container.init().add(this)
    return this
}

interface DeepCopyable {
    fun deepCopy(other: Any)
    fun newInstance(): DeepCopyable
}

abstract class IdentifiableItem: DeepCopyable {
    var id: String? = null

    override fun deepCopy(other: Any) {
        if( other is IdentifiableItem ) {
            this.id = other.id
        } else {
            throw Exception()
        }
    }
}

abstract class NameableItem: IdentifiableItem() {
    var name: String? = null
    var description: String? = null

    override fun deepCopy(other: Any) {
        super.deepCopy(other)

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
    var ordinal: Int? = null
    var hide: Boolean? = null
    var requirements: ArrayList<RequirementTree>? = null

    override fun deepCopy(other: Any) {
        super.deepCopy(other)

        if( other is AdventureItem ) {
            this.ordinal = other.ordinal
            this.hide = other.hide
            this.requirements = deepCopyList(other.requirements)
        } else {
            throw Exception()
        }
    }
}