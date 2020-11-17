package it.thefreak.android.interactivecyoaeditor.model

import it.thefreak.android.interactivecyoaeditor.ui.editor.components.IdManager

open class Adventure: NarrativeItem() {
    var versionName: String? = null
    var versionCode: Int? = null
    var author: String? = null
    var initialPoints: ArrayList<PointState>? = null
    var adventureNodesList: ArrayList<AdventureNode>? = null

    override fun deepCopy(other: Any, idManager: IdManager) {
        super.deepCopy(other, idManager)

        if( other is Adventure ) {
            this.versionName = other.versionName
            this.versionCode = other.versionCode
            this.author = other.author
            this.initialPoints = deepCopyList(other.initialPoints, idManager)
            this.adventureNodesList = deepCopyList(other.adventureNodesList, idManager)
        } else {
            throw Exception()
        }
    }

    override fun newInstance(): IdentifiableItem = Adventure()
}