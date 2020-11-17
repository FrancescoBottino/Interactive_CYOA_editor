package it.thefreak.android.interactivecyoaeditor.model

class Cost: DeepCopyable {
    var pointType: PointType? = null
    var amount: Int? = null
    var hide: Boolean? = null
    var modifiers: ArrayList<CostModifier>? = null

    override fun deepCopy(other: Any) {
        if( other is Cost ) {
            this.pointType = other.pointType
            this.amount = other.amount
            this.hide = other.hide
            this.modifiers = deepCopyList(other.modifiers)
        } else {
            throw Exception()
        }
    }

    override fun newInstance(): DeepCopyable = Cost()
}