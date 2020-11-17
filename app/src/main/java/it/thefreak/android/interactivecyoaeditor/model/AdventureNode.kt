package it.thefreak.android.interactivecyoaeditor.model

import it.thefreak.android.interactivecyoaeditor.ui.editor.components.IdManager

class AdventureNode: AdventureItem() {
    enum class ChoicesType {
        OPTIONAL, REQUIRED
    }

    var nodeSpecificPoints: ArrayList<PointState>? = null
    var choiceLimit: Int? = null
    var choiceType: ChoicesType = ChoicesType.OPTIONAL
    var choicesList: ArrayList<Choice>? = null

    override fun deepCopy(other: Any, idManager: IdManager) {
        super.deepCopy(other, idManager)

        if( other is AdventureNode ) {
            this.choiceLimit = other.choiceLimit
            this.choiceType = other.choiceType
            this.choicesList = deepCopyList(other.choicesList, idManager)
            this.nodeSpecificPoints = deepCopyList(other.nodeSpecificPoints, idManager)
        } else {
            throw Exception()
        }
    }

    override fun newInstance(): IdentifiableItem = AdventureNode()
}