package it.thefreak.android.interactivecyoaeditor

import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

object JsonFileHandler {
    inline fun <reified T> saveToJsonFile(file: File, content: T, format: Json = Json) {
        val serializedContent = content.toJson(format)
        file.apply {
            FileOutputStream(this).use { outputStream ->
                outputStream.write(serializedContent.toByteArray())
            }
        }
    }

    inline fun <reified T> loadFromJsonFile(file: File, format: Json = Json): T? {
        if( !file.exists() ) return null

        val bytes = ByteArray(file.length().toInt())
        FileInputStream(file).use { inputStream ->
            inputStream.read(bytes)
        }
        return String(bytes).fromJson<T>(format)
    }
}