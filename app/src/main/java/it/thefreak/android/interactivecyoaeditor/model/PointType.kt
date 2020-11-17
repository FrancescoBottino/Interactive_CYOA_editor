package it.thefreak.android.interactivecyoaeditor.model

open class PointType: AdventureItem() {
    var canGoUnderZero: Boolean? = null

    override fun deepCopy(other: Any) {
        super.deepCopy(other)

        if( other is PointType ) {
            this.canGoUnderZero = other.canGoUnderZero
        } else {
            throw Exception()
        }
    }

    override fun newInstance(): DeepCopyable = PointType()
}