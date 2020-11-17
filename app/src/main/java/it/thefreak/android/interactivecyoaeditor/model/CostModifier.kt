package it.thefreak.android.interactivecyoaeditor.model

class CostModifier: DeepCopyable {
    var hide: Boolean? = null
    var requirements: ArrayList<RequirementTree>? = null
    var amount: Int? = null

    override fun deepCopy(other: Any) {
        if( other is CostModifier ) {
            this.amount = other.amount
            this.hide = other.hide
            this.requirements = deepCopyList(other.requirements)
        } else {
            throw Exception()
        }
    }

    override fun newInstance(): DeepCopyable = CostModifier()
}