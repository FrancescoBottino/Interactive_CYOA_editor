package it.thefreak.android.interactivecyoaeditor.utils

object UniqueIdGenerator {
    class IdException(message: String): Exception(message)

    const val len = 6
    val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    fun generateId(): String {
        return (1..len)
                .map { kotlin.random.Random.nextInt(0, charPool.size) }
                .map(charPool::get)
                .joinToString("")
    }

    fun getNewId(uniquenessCheck: (String)->Boolean): String {
        var newId: String
        do {
            newId = generateId()
        } while (!uniquenessCheck(newId))
        return newId
    }

    fun String?.isIdValid() {
        if(this == null)
            throw IdException("Id is null")
        if(isNullOrBlank())
            throw IdException("Id is blank")
        if(length != len)
            throw IdException("Id length not 6")
        if(any { c -> c !in charPool })
            throw IdException("Id contains forbidden chars")
    }
}