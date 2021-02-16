package it.thefreak.android.interactivecyoaeditor.model

import kotlin.reflect.KClass

class IdManager {
    companion object {
        var len = 6
        private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    }

    val idMap: HashMap<String, IdentifiableItem> by lazy {
        HashMap()
    }

    fun generateId(): String {
        return (1..len)
                .map { kotlin.random.Random.nextInt(0, charPool.size) }
                .map(charPool::get)
                .joinToString("")
    }

    fun getNewId(): String {
        var newId: String
        do {
            newId = generateId()
        } while (idMap.containsKey(newId))
        return newId
    }

    fun addWithNewId(obj: IdentifiableItem): String {
        return getNewId().apply {
            idMap[this] = obj
        }
    }

    fun addWithCurrentId(obj: IdentifiableItem, checkForUsed: Boolean = true) {
        if(obj.id.isNullOrBlank() || obj.id?.length != 6) throw Exception()
        if(checkForUsed && idMap.containsKey(obj.id)) throw Exception()

        idMap[obj.id!!] = obj
    }

    fun remove(obj: IdentifiableItem) {
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
    inline fun <reified T: IdentifiableItem> findByType(): Map<String, T> {
        return idMap.filter { (_,v) -> v is T } as Map<String, T>
    }

    @Suppress("UNCHECKED_CAST")
    fun <T: IdentifiableItem> findByType(type: KClass<T>): Map<String, T> {
        return idMap.filter { (_,v) -> v::class == type } as Map<String, T>
    }
}