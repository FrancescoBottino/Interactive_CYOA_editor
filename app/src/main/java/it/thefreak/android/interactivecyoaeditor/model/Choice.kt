package it.thefreak.android.interactivecyoaeditor.model

open class Choice: AdventureItem() {
    var automaticallyActivated: Boolean? = null
    var buyLimit: Int? = null
    var costs: ArrayList<Cost>? = null
    var subChoices: ArrayList<ChoicesGroup>? = null

    override fun deepCopy(other: Any) {
        super.deepCopy(other)

        if( other is Choice ) {
            this.automaticallyActivated = other.automaticallyActivated
            this.buyLimit = other.buyLimit
            this.costs = deepCopyList(other.costs)
            this.subChoices = deepCopyList(other.subChoices)
        } else {
            throw Exception()
        }
    }

    override fun newInstance(): DeepCopyable = Choice()
}