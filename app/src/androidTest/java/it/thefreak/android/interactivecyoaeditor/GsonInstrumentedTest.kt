package it.thefreak.android.interactivecyoaeditor

import androidx.test.ext.junit.runners.AndroidJUnit4
import it.thefreak.android.interactivecyoaeditor.model.entities.Adventure
import it.thefreak.android.interactivecyoaeditor.model.entities.AdventureMeta
import it.thefreak.android.interactivecyoaeditor.model.format
import it.thefreak.android.interactivecyoaeditor.utils.deserializationExcluderGson
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class GsonInstrumentedTest {
    @Test
    fun extractAdventureToMetadataTest() {
        val advName = "test adventure"
        val advDesc = "test description, hello world"
        val adv = Adventure(name = advName, description = advDesc)
        val serializedContent = adv.toJson(format)

        val readAdvMeta = deserializationExcluderGson.fromJson(serializedContent, Adventure::class.java).getMetadata()

        assert( advName == readAdvMeta.name )
        assert( advDesc == readAdvMeta.description )
    }

    @Test
    fun extractAdventureAsMetadataTest() {
        val advName = "test adventure"
        val advDesc = "test description, hello world"
        val adv = Adventure(name = advName, description = advDesc)
        val serializedContent = adv.toJson(format)

        val readAdvMeta = deserializationExcluderGson.fromJson(serializedContent, AdventureMeta::class.java)

        assert( advName == readAdvMeta.name )
        assert( advDesc == readAdvMeta.description )
    }
}