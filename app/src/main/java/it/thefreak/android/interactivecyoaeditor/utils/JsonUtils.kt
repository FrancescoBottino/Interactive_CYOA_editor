package it.thefreak.android.interactivecyoaeditor

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

inline fun <reified T> T.toJson(format: Json = Json): String {
    return format.encodeToString(this)
}
inline fun <reified T> String.fromJson(format: Json = Json): T {
    return format.decodeFromString(this)
}