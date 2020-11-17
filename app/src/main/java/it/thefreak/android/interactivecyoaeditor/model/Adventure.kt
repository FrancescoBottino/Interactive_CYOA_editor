package it.thefreak.android.interactivecyoaeditor.model

open class Adventure: NarrativeItem() {
    var versionName: String? = null
    var versionCode: Int? = null
    var author: String? = null
    var initialPoints: ArrayList<PointState>? = null
    var adventureItemsList: ArrayList<AdventureItem>? = null

    override fun deepCopy(other: Any) {
        super.deepCopy(other)

        if( other is Adventure ) {
            this.versionName = other.versionName
            this.versionCode = other.versionCode
            this.author = other.author
            this.initialPoints = deepCopyList(other.initialPoints)
            this.adventureItemsList = deepCopyList(other.adventureItemsList)
        } else {
            throw Exception()
        }
    }

    override fun newInstance(): DeepCopyable = Adventure()
}