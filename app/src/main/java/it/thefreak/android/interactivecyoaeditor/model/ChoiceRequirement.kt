package it.thefreak.android.interactivecyoaeditor.model

data class ChoiceRequirement(
        override var id: String? = null,
        override var ordinal: Int? = null,
        override var icon: String? = null,
        var groupingFunction: GroupingFunction? = null,
        var choicesIds: ArrayList<String>? = null,
) : Requirement {
    override val type: RequirementType = RequirementType.CHOICE_SELECTION
}
