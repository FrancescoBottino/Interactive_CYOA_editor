package it.thefreak.android.interactivecyoaeditor.model

import it.thefreak.android.interactivecyoaeditor.model.itemtypes.IdManageableItem
import it.thefreak.android.interactivecyoaeditor.utils.UniqueIdGenerator
import it.thefreak.android.interactivecyoaeditor.utils.UniqueIdGenerator.isIdValid
import kotlin.reflect.KClass

class IdManager {
    val idMap: HashMap<String, IdManageableItem> by lazy {
        HashMap()
    }

    fun getNewId(): String = UniqueIdGenerator.getNewId { newId -> !idMap.containsKey(newId) }

    fun addWithNewId(obj: IdManageableItem): String {
        return getNewId().apply {
            idMap[this] = obj
        }
    }

    fun addWithCurrentId(obj: IdManageableItem, checkForUsed: Boolean = true) {
        obj.id?.let { id ->
            id.isIdValid()
            if(checkForUsed && idMap.containsKey(id))
                throw UniqueIdGenerator.IdException("Id already in use")

            idMap[id] = obj
        }
    }

    fun remove(obj: IdManageableItem) {
        if(!idMap.containsValue(obj)) throw Exception()
        idMap.remove(obj.id)
    }

    fun editId(oldId: String, newId: String) {
        if( oldId == newId ) return
        if( idMap.containsKey(oldId) ) throw Exception()

        val item = idMap[oldId]!!
        idMap.remove(oldId)
        idMap[newId] = item
    }

    @Suppress("UNCHECKED_CAST")
    inline fun <reified T: IdManageableItem> findByType(): Map<String, T> {
        return idMap.filter { (_,v) -> v is T } as Map<String, T>
    }

    @Suppress("UNCHECKED_CAST")
    fun <T: IdManageableItem> findByType(type: KClass<T>): Map<String, T> {
        return idMap.filter { (_,v) -> v::class == type } as Map<String, T>
    }
}