package it.thefreak.android.interactivecyoaeditor

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


inline fun <reified T> T.toJson(): String {
    return Json.encodeToString(this)
}
inline fun <reified T> String.fromJson(): T {
    return Json.decodeFromString(this)
}

/*
inline fun <reified T> T.toJson(): String {
    return Moshi
            .Builder()
            .build()
            .adapter(T::class.java)
            .nullSafe()
            .toJson(this)
}

inline fun <reified T> String.fromJson(): T? {
    return Moshi
            .Builder()
            .build()
            .adapter(T::class.java)
            .nullSafe()
            .fromJson(this)
}

abstract class MoshiArrayListJsonAdapter<C : MutableCollection<T>?, T> private constructor(
    private val elementAdapter: JsonAdapter<T>
) :
    JsonAdapter<C>() {
    abstract fun newCollection(): C

    @Throws(IOException::class)
    override fun fromJson(reader: JsonReader): C {
        val result = newCollection()
        reader.beginArray()
        while (reader.hasNext()) {
            result?.add(elementAdapter.fromJson(reader)!!)
        }
        reader.endArray()
        return result
    }

    @Throws(IOException::class)
    override fun toJson(writer: JsonWriter, value: C?) {
        writer.beginArray()
        for (element in value!!) {
            elementAdapter.toJson(writer, element)
        }
        writer.endArray()
    }

    override fun toString(): String {
        return "$elementAdapter.collection()"
    }

    companion object {
        val FACTORY = Factory { type, annotations, moshi ->
            val rawType = Types.getRawType(type)
            if (annotations.isNotEmpty()) return@Factory null
            if (rawType == ArrayList::class.java) {
                return@Factory newArrayListAdapter<Any>(
                    type,
                    moshi
                ).nullSafe()
            }
            null
        }

        private fun <T> newArrayListAdapter(
            type: Type,
            moshi: Moshi
        ): JsonAdapter<MutableCollection<T>> {
            val elementType =
                Types.collectionElementType(
                    type,
                    MutableCollection::class.java
                )

            val elementAdapter: JsonAdapter<T> = moshi.adapter(elementType)

            return object :
                MoshiArrayListJsonAdapter<MutableCollection<T>, T>(elementAdapter) {
                override fun newCollection(): MutableCollection<T> {
                    return ArrayList()
                }
            }
        }
    }
}

 */

/*
class ArrayListAdapter<T>: JsonAdapter<ArrayList<T>>() {
    @ToJson
    fun toJson(itemList: ArrayList<T>): String {
        return (itemList as List<T>).toJson()
    }

    @FromJson
    fun fromJson(itemList: String): ArrayList<T> {
        return ArrayList<T>().apply {
            addAll(itemList.fromJson<List<T>>()?:listOf())
        }
    }

    override fun toJson(writer: JsonWriter, value: ArrayList<T>?) {
        TODO("Not yet implemented")
    }

    override fun fromJson(reader: JsonReader): ArrayList<T>? {
        TODO("Not yet implemented")
    }
}

 */