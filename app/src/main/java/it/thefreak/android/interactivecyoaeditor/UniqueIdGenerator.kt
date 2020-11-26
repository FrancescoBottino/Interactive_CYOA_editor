package it.thefreak.android.interactivecyoaeditor

object UniqueIdGenerator {
    private const val len = 6
    private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    private fun generateId(): String {
        return (1..len)
                .map { kotlin.random.Random.nextInt(0, charPool.size) }
                .map(charPool::get)
                .joinToString("");
    }

    fun getNewId(uniquenessCheck: (String)->Boolean): String {
        var newId: String
        do {
            newId = generateId()
        } while (!uniquenessCheck(newId))
        return newId
    }
}