package it.thefreak.android.interactivecyoaeditor.model

open class RequirementTree: IdentifiableItem(), DeepCopyable {
    var root: Requirement? = null

    abstract class Requirement: IdentifiableItem(), DeepCopyable {
    }

    open class ChoiceRequirement (
            var choice: Choice,
            var type: ChoiceRequirementType
    ) : Requirement() {
        enum class ChoiceRequirementType {
            MUST_HAVE, MUST_NOT_HAVE
        }
        override fun deepCopy(other: Any) {
            TODO("Not yet implemented")
        }
        override fun newInstance(): DeepCopyable {
            TODO("Not yet implemented")
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
        override fun deepCopy(other: Any) {
            TODO("Not yet implemented")
        }
        override fun newInstance(): DeepCopyable {
            TODO("Not yet implemented")
        }
    }
    open class RequirementLogicNode(
            var type: RequirementLogicNodeType,
            var children: ArrayList<Requirement>
    ) : Requirement() {
        enum class RequirementLogicNodeType {
            AND, OR, NOT
        }
        override fun deepCopy(other: Any) {
            TODO("Not yet implemented")
        }
        override fun newInstance(): DeepCopyable {
            TODO("Not yet implemented")
        }
    }
    open class RequirementLogicNodeNot(
            var child: Requirement
    ) : RequirementLogicNode(RequirementLogicNodeType.NOT, arrayListOf(child)) {
        override fun deepCopy(other: Any) {
            TODO("Not yet implemented")
        }
        override fun newInstance(): DeepCopyable {
            TODO("Not yet implemented")
        }
    }

    override fun deepCopy(other: Any) {
        TODO("Not yet implemented")
        /*
        super.deepCopy(other)

        if( other is RequirementTree ) {
            this.root = other.root?.let {
                it.newInstance().apply {
                    deepCopy(it)
                } as Requirement
            }
        } else {
            throw Exception()
        }

         */
    }
    override fun newInstance(): DeepCopyable = RequirementTree()
}