package it.thefreak.android.interactivecyoaeditor.model

import it.thefreak.android.interactivecyoaeditor.ui.editor.components.IdManager

open class Choice: AdventureItem() {
    var automaticallyActivated: Boolean? = null
    var buyLimit: Int? = null
    var costs: ArrayList<Cost>? = null
    var subChoices: ArrayList<AdventureNode>? = null

    override fun deepCopy(other: Any, idManager: IdManager) {
        super.deepCopy(other, idManager)

        if( other is Choice ) {
            this.automaticallyActivated = other.automaticallyActivated
            this.buyLimit = other.buyLimit
            this.costs = deepCopyList(other.costs, idManager)
            this.subChoices = deepCopyList(other.subChoices, idManager)
        } else {
            throw Exception()
        }
    }

    override fun newInstance(): IdentifiableItem = Choice()
}