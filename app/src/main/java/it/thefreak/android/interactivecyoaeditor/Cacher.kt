package it.thefreak.android.interactivecyoaeditor

import android.content.Context
import it.thefreak.android.interactivecyoaeditor.FileSelector.getFile
import it.thefreak.android.interactivecyoaeditor.FileSelector.getTopFile
import it.thefreak.android.interactivecyoaeditor.JsonFileHandler.loadFromJsonFile
import it.thefreak.android.interactivecyoaeditor.JsonFileHandler.saveToJsonFile
import java.io.File

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

    fun getBeanFile(ctx: Context, key: String): File {
        return getFile(getTopFile(ctx, folderName).apply {mkdir()}, getFileName(key))
    }

    inline fun <reified T> savePersistent(ctx: Context, key: String, bean: T) {
        getBeanFile(ctx, key).let { jsonFile ->
            saveToJsonFile(jsonFile, bean)
        }
    }
    inline fun <reified T> loadPersistent(ctx: Context, key: String): T? {
        getBeanFile(ctx, key).let { jsonFile ->
            return loadFromJsonFile(jsonFile)
        }
    }
}