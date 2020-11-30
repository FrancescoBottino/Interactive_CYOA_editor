package it.thefreak.android.interactivecyoaeditor.model

import kotlin.reflect.KMutableProperty0

fun String?.copy(): String? = this?.let { ""+it }
fun <T:IdentifiableItem> ArrayList<T>?.copy(idManager: IdManager): ArrayList<T>? {
    return this?.let { list ->
        ArrayList<T>().apply {
            addAll(list.map { it.deepCopy(idManager) as T })
        }
    }
}
fun ArrayList<String>?.copy(): ArrayList<String>? {
    return this?.let { list ->
        ArrayList<String>().apply {
            addAll(list.map { it.copy()!! })
        }
    }
}

fun <T: IdentifiableItem> T.assignNewId(idManager: IdManager): T {
    return this.apply {
        id = idManager.addWithNewId(this)
    }
}

fun <T: IdentifiableItem> T.addWithCurrentId(idManager: IdManager): T {
    return this.apply {
        idManager.addWithCurrentId(this)
    }
}

fun <T: IdentifiableItem> T.removeFromIdMap(idManager: IdManager): T {
    return this.apply {
        idManager.remove(this)
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

