package it.thefreak.android.interactivecyoaeditor.utils

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.Gson
import com.google.gson.GsonBuilder


@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class Exclude

val excluderStrategy: ExclusionStrategy by lazy {
    object: ExclusionStrategy {
        override fun shouldSkipClass(clazz: Class<*>?): Boolean {
            return false
        }

        override fun shouldSkipField(field: FieldAttributes): Boolean {
            return field.getAnnotation(Exclude::class.java) != null
        }
    }
}

val excluderGson: Gson by lazy {
    GsonBuilder().setExclusionStrategies(excluderStrategy).create()
}
val deserializationExcluderGson: Gson by lazy {
    GsonBuilder().addDeserializationExclusionStrategy(excluderStrategy).create()
}
val serializationExcluderGson: Gson by lazy {
    GsonBuilder().addSerializationExclusionStrategy(excluderStrategy).create()
}