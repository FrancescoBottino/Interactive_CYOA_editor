package it.thefreak.android.interactivecyoaeditor

import android.content.Context
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.lang.reflect.Type

object Cacher {
    private const val folderName = "json_cache"
    private val reservedChars = arrayOf("|", "\\", "?", "*", "<", "\"", ":", ">")
    private val map: HashMap<String, Any> by lazy {
        HashMap()
    }

    fun save(key: String, bean: Any) {
        map[key] = bean
    }
    fun load(key: String): Any? {
        return map[key]
    }

    private fun getFileName(key: String): String {
        reservedChars.forEach {
            if(key.contains(it))
                throw Exception("invalid key($key) to cache (contains forbidden character $it)")
        }
        return "$key.json"
    }

    fun savePersistent(ctx: Context, key: String, bean: Any) {
        save(key, bean)
        File(ctx.filesDir, folderName).apply {
            mkdir()
            File(this, getFileName(key)).apply {
                FileOutputStream(this).use { outputStream ->
                    outputStream.write(bean.toJson().toByteArray())
                }
            }
        }
    }
    fun loadPersistent(ctx: Context, key: String, type: Type): Any? {
        load(key)?.let {
            return it
        }

        File(ctx.filesDir, folderName).let { folder ->
            if( !folder.exists() ) return null
            File(folder, getFileName(key)).let { file ->
                if( !file.exists() ) return null

                val bytes = ByteArray(file.length().toInt())
                FileInputStream(file).use { inputStream ->
                    inputStream.read(bytes)
                }
                return String(bytes).fromJson<Type>()
            }
        }
    }
}