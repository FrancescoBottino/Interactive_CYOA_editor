package it.thefreak.android.interactivecyoaeditor.ui.editor.forms.adventure

import android.content.Context
import com.zhuinden.simplestack.Backstack
import it.thefreak.android.interactivecyoaeditor.model.Adventure
import it.thefreak.android.interactivecyoaeditor.model.IdManager
import it.thefreak.android.interactivecyoaeditor.model.deepRegisterItem

class AdventureFormModel(
    private val adventureFormKey: AdventureFormKey,
    private val backstack: Backstack
) {
    private var adventure: Adventure? = null
    fun getAdventure(ctx: Context): Adventure {
        return if(adventure == null) {
            loadAction(ctx).apply {
                adventure = this
                deepRegisterItem(idManager)
            }
        } else {
            adventure!!
        }
    }
    val idManager: IdManager by lazy {
        IdManager()
    }
}
