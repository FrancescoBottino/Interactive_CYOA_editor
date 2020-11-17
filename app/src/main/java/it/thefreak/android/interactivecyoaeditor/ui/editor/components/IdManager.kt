package it.thefreak.android.interactivecyoaeditor.ui.editor.components

import it.thefreak.android.interactivecyoaeditor.model.IdentifiableItem

class IdManager {
    companion object {
        var len = 6
        private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    }

    val idMap: HashMap<String, IdentifiableItem> by lazy {
        HashMap()
    }

    private fun generateId(): String {
        return (1..len)
                .map { kotlin.random.Random.nextInt(0, charPool.size) }
                .map(charPool::get)
                .joinToString("");
    }

    private fun getNewId(): String {
        var newId: String
        do {
            newId = generateId()
        } while (idMap.containsKey(newId))
        return newId
    }

    fun add(obj: IdentifiableItem): String {
        if(idMap.containsValue(obj)) throw Exception()
        return getNewId().apply {
            idMap[this] = obj
        }
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

    inline fun <reified T> findByType(): List<IdentifiableItem> {
        return idMap.map { entry -> entry.value }.filter { item -> item is T }
    }
}