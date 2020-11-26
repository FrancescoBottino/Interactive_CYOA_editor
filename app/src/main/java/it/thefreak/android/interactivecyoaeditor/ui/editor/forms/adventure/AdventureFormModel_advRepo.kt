package it.thefreak.android.interactivecyoaeditor.ui.editor.forms.adventure

import android.net.Uri
import androidx.core.net.toFile
import it.thefreak.android.interactivecyoaeditor.JsonFileHandler.loadFromJsonFile
import it.thefreak.android.interactivecyoaeditor.model.Adventure
import it.thefreak.android.interactivecyoaeditor.model.IdManager
import it.thefreak.android.interactivecyoaeditor.model.deepLinkItem
import it.thefreak.android.interactivecyoaeditor.model.deepRegisterItem

class AdventureFormModel_advRepo(
        /*
    private val adventureFormKey: AdventureFormKey,
    private val backstack: Backstack,

         */
    private val advUri: Uri
) {
    private var adventure: Adventure? = null
    fun getAdventure(idManager: IdManager): Adventure {
        return if(adventure == null) {
            loadFromJsonFile<Adventure>(advUri.toFile())?.apply {
                adventure = this
                deepRegisterItem(idManager)
                deepLinkItem(idManager)
            } ?: throw Exception("cant read file $advUri")
        } else {
            adventure!!
        }
    }
    fun saveAdventure(adventure: Adventure) {

    }
}
