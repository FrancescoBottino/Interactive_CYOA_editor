package it.thefreak.android.interactivecyoaeditor.model

open class PointState: PointType() {
    var amount: Int? = null

    override fun deepCopy(other: Any) {
        super.deepCopy(other)

        if( other is PointState ) {
            this.amount = other.amount
        } else {
            throw Exception()
        }
    }

    override fun newInstance(): DeepCopyable = PointState()
}