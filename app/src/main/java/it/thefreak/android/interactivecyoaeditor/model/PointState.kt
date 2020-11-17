package it.thefreak.android.interactivecyoaeditor.model

import it.thefreak.android.interactivecyoaeditor.ui.editor.components.IdManager

open class PointState: PointType() {
    var amount: Int? = null

    override fun deepCopy(other: Any, idManager: IdManager) {
        super.deepCopy(other, idManager)

        if( other is PointState ) {
            this.amount = other.amount
        } else {
            throw Exception()
        }
    }

    override fun newInstance(): IdentifiableItem = PointState()
}