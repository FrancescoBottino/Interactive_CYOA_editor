package it.thefreak.android.interactivecyoaeditor.ui.editor.forms.adventure

import com.zhuinden.simplestack.Backstack
import it.thefreak.android.interactivecyoaeditor.model.Adventure
import it.thefreak.android.interactivecyoaeditor.ui.editor.components.IdManager

class AdventureFormModel(
    private val adventureFormKey: AdventureFormKey,
    private val backstack: Backstack
) {
    val adventure: Adventure by lazy {
        Adventure()
    }
    val idManager: IdManager by lazy {
        IdManager()
    }
}