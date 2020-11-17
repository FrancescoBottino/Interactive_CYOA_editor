package it.thefreak.android.interactivecyoaeditor.model

open class StoryElement: AdventureItem() {
    var adventureItemsList: ArrayList<AdventureItem>? = null

    override fun deepCopy(other: Any) {
        super.deepCopy(other)

        if( other is StoryElement ) {
            this.adventureItemsList = deepCopyList(other.adventureItemsList)
        } else {
            throw Exception()
        }
    }

    override fun newInstance(): DeepCopyable = StoryElement()
}