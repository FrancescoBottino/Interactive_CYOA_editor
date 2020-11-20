package it.thefreak.android.interactivecyoaeditor

import android.content.Context
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

object Cacher {
    const val folderName = "json_cache"
    val reservedChars = arrayOf("|", "\\", "?", "*", "<", "\"", ":", ">")
    val map: HashMap<String, Any> by lazy {
        HashMap()
    }

    fun save(key: String, bean: Any) {
        map[key] = bean
    }
    fun load(key: String): Any? {
        return map[key]
    }

    fun getFileName(key: String): String {
        reservedChars.forEach {
            if(key.contains(it))
                throw Exception("invalid key($key) to cache (contains forbidden character $it)")
        }
        return "$key.json"
    }

    inline fun <reified T> savePersistent(ctx: Context, key: String, bean: T) {
        val serializedBean = bean.toJson()
        File(ctx.filesDir, folderName).apply {
            mkdir()
            File(this, getFileName(key)).apply {
                FileOutputStream(this).use { outputStream ->
                    outputStream.write(serializedBean.toByteArray())
                }
            }
        }
    }
    inline fun <reified T> loadPersistent(ctx: Context, key: String): T? {
        File(ctx.filesDir, folderName).let { folder ->
            if( !folder.exists() ) return null
            File(folder, getFileName(key)).let { file ->
                if( !file.exists() ) return null

                val bytes = ByteArray(file.length().toInt())
                FileInputStream(file).use { inputStream ->
                    inputStream.read(bytes)
                }
                return String(bytes).fromJson<T>()
            }
        }
    }
}