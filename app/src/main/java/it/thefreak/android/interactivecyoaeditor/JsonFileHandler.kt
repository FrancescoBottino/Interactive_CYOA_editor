package it.thefreak.android.interactivecyoaeditor

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

object JsonFileHandler {
    inline fun <reified T> saveToJsonFile(file: File, content: T) {
        val serializedContent = content.toJson()
        file.apply {
            FileOutputStream(this).use { outputStream ->
                outputStream.write(serializedContent.toByteArray())
            }
        }
    }

    inline fun <reified T> loadFromJsonFile(file: File): T? {
        if( !file.exists() ) return null

        val bytes = ByteArray(file.length().toInt())
        FileInputStream(file).use { inputStream ->
            inputStream.read(bytes)
        }
        return String(bytes).fromJson<T>()
    }
}