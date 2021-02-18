package it.thefreak.android.interactivecyoaeditor.ui.editor.forms.adventure

import android.net.Uri
import androidx.core.net.toFile
import it.thefreak.android.interactivecyoaeditor.model.IdManager
import it.thefreak.android.interactivecyoaeditor.model.entities.Adventure
import it.thefreak.android.interactivecyoaeditor.model.format
import it.thefreak.android.interactivecyoaeditor.utils.JsonFileHandler.loadFromJsonFile
import it.thefreak.android.interactivecyoaeditor.utils.JsonFileHandler.saveToJsonFile

class AdventureFormModelLoaderRepository(
    private val advUri: Uri
) {
    private var adventure: Adventure? = null
    fun getAdventure(idManager: IdManager): Adventure {
        return if(adventure == null) {
            loadFromJsonFile<Adventure>(advUri.toFile(), format)?.apply {
                adventure = this
                deepRegister(idManager)
            } ?: throw Exception("cant read file $advUri")
        } else {
            adventure!!
        }
    }
    fun saveAdventure(adventure: Adventure) {
        saveToJsonFile(advUri.toFile(), adventure, format)
    }
}
