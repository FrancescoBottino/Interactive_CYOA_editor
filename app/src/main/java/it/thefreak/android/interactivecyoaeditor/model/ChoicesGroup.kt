package it.thefreak.android.interactivecyoaeditor.model

open class ChoicesGroup: StoryElement() {
    enum class ChoicesGroupType {
        OPTIONAL, REQUIRED
    }

    var groupSpecificPoints: ArrayList<PointState>? = null
    var choiceLimit: Int? = null
    var type: ChoicesGroupType = ChoicesGroupType.OPTIONAL
    var choicesList: ArrayList<Choice>? = null

    override fun deepCopy(other: Any) {
        super.deepCopy(other)

        if( other is ChoicesGroup ) {
            this.choiceLimit = other.choiceLimit
            this.type = other.type
            this.choicesList = deepCopyList(other.choicesList)
            this.groupSpecificPoints = deepCopyList(other.groupSpecificPoints)
        } else {
            throw Exception()
        }
    }

    override fun newInstance(): DeepCopyable = ChoicesGroup()
}