package it.thefreak.android.interactivecyoaeditor.model

import it.thefreak.android.interactivecyoaeditor.ui.editor.components.IdManager

class Cost: IdentifiableItem() {
    var pointType: PointType? = null
    var amount: Int? = null
    var hide: Boolean? = null
    var modifiers: ArrayList<CostModifier>? = null

    override fun deepCopy(other: Any, idManager: IdManager) {
        super.deepCopy(other, idManager)

        if( other is Cost ) {
            this.pointType = other.pointType
            this.amount = other.amount
            this.hide = other.hide
            this.modifiers = deepCopyList(other.modifiers, idManager)
        } else {
            throw Exception()
        }
    }

    override fun newInstance(): IdentifiableItem = Cost()
}