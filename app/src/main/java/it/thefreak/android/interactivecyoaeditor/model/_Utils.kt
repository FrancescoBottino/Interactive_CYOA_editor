package it.thefreak.android.interactivecyoaeditor.model

import it.thefreak.android.interactivecyoaeditor.model.itemtypes.IdManageableItem
import kotlin.reflect.KMutableProperty0

fun String?.copy(): String? = this?.let { ""+it }
fun <T: IdManageableItem> ArrayList<T>?.copy(idManager: IdManager): ArrayList<T>? {
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
fun <T: IdManageableItem> HashSet<T>?.copy(idManager: IdManager): HashSet<T>? {
    return this?.let { set ->
        HashSet<T>().apply {
            addAll( set.map { it.deepCopy(idManager) as T } )
        }
    }
}
fun HashSet<String>?.copy(): HashSet<String>? {
    return this?.let { set ->
        HashSet<String>().apply {
            addAll(set.map { it.copy()!! })
        }
    }
}

fun <T: IdManageableItem> T.assignNewId(idManager: IdManager): T {
    return this.apply {
        id = idManager.addWithNewId(this)
    }
}

fun <T: IdManageableItem> T.addWithCurrentId(idManager: IdManager): T {
    return this.apply {
        idManager.addWithCurrentId(this)
    }
}

fun <T: IdManageableItem> T.removeFromIdMap(idManager: IdManager): T {
    return this.apply {
        idManager.remove(this)
    }
}

fun <T> KMutableProperty0<ArrayList<T>?>.init(): ArrayList<T> {
    return this.let {
        var arrList = this.get()
        if(arrList == null) {
            arrList = ArrayList()
            it.set(arrList)
        }
        arrList
    }
}

fun <T> KMutableProperty0<HashSet<T>?>.init(): HashSet<T> {
    return this.let {
        var hashSet = this.get()
        if(hashSet == null) {
            hashSet = HashSet()
            it.set(hashSet)
        }
        hashSet
    }
}

