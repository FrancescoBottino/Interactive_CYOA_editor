package it.thefreak.android.interactivecyoaeditor.model

import it.thefreak.android.interactivecyoaeditor.ui.editor.components.IdManager

open class PointType: AdventureItem() {
    var canGoUnderZero: Boolean? = null

    override fun deepCopy(other: Any, idManager: IdManager) {
        super.deepCopy(other, idManager)

        if( other is PointType ) {
            this.canGoUnderZero = other.canGoUnderZero
        } else {
            throw Exception()
        }
    }

    override fun newInstance(): IdentifiableItem = PointType()
}