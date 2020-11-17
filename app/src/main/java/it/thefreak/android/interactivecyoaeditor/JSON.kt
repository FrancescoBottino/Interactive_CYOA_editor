package it.thefreak.android.interactivecyoaeditor

import com.squareup.moshi.Moshi

inline fun <reified T> T.toJson(): String {
    return Moshi
            .Builder()
            .build()
            .adapter(T::class.java)
            .toJson(this)
}

inline fun <reified T> String.fromJson(): T? {
    return Moshi
            .Builder()
            .build()
            .adapter(T::class.java)
            .fromJson(this)
}
//inline fun <reified T> T.deepCopy(): T? = this.toJson().fromJson()