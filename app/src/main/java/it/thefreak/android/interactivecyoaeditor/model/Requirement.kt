package it.thefreak.android.interactivecyoaeditor.model


interface Requirement: IdentifiableItem, ListableItem {
    val type: RequirementType
}

/*
open class Requirement: IdentifiableItem() {
}

open class ChoiceRequirement (
        var choice: Choice,
        var type: ChoiceRequirementType
) : Requirement() {
    enum class ChoiceRequirementType {
        MUST_HAVE, MUST_NOT_HAVE
    }
}

open class PointsRequirement(
        var pointType: PointType,
        var amount: Int,
        var type: PointsRequirementType
) : Requirement() {
    enum class PointsRequirementType {
        EQUAL, LESS_THAN, LESS_OR_EQUAL_THAN, MORE_THAN, MORE_OR_EQUAL_THAN
    }
}

open class RequirementLogicNode(
        var type: RequirementLogicNodeType,
        var children: ArrayList<Requirement>
) : Requirement() {
    enum class RequirementLogicNodeType {
        AND, OR, NOT
    }
}

open class RequirementLogicNodeNot(
        var child: Requirement
) : RequirementLogicNode(RequirementLogicNodeType.NOT, arrayListOf(child)) {
}
*/