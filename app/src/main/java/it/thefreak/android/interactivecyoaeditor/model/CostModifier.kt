package it.thefreak.android.interactivecyoaeditor.model

import it.thefreak.android.interactivecyoaeditor.ui.editor.components.IdManager

class CostModifier: IdentifiableItem() {
    var hide: Boolean? = null
    var requirements: ArrayList<RequirementTree>? = null
    var amount: Int? = null

    override fun deepCopy(other: Any, idManager: IdManager) {
        super.deepCopy(other, idManager)

        if( other is CostModifier ) {
            this.amount = other.amount
            this.hide = other.hide
            this.requirements = deepCopyList(other.requirements, idManager)
        } else {
            throw Exception()
        }
    }

    override fun newInstance(): IdentifiableItem = CostModifier()
}