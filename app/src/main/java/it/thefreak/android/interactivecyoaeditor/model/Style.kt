package it.thefreak.android.interactivecyoaeditor.model

import it.thefreak.android.interactivecyoaeditor.ui.editor.components.IdManager

open class Style(): IdentifiableItem() {
    //TODO OTHER OPTIONS
    var bgColorCode: String? = null
    //var font: Font? = null
    var textColorCode: String? = null

    override fun deepCopy(other: Any, idManager: IdManager) {
        TODO("Not yet implemented")
    }
    override fun newInstance(): IdentifiableItem {
        TODO("Not yet implemented")
    }
}