package it.thefreak.android.interactivecyoaeditor.model

import kotlinx.serialization.Serializable

@Serializable
data class PointComparisonRequirement(
        override var id: String? = null,
        override var ordinal: Int? = null,
        override var icon: String? = null,
        var pointTypeAId: String? = null,
        var comparisonFunction: ComparisonFunction? = null,
        var pointTypeBId: String? = null,
): Requirement {
    override val type: RequirementType = RequirementType.POINT_COMPARISON
    override fun deepCopy(idManager: IdManager): PointComparisonRequirement {
        return PointComparisonRequirement(
                icon = this.icon.copy(),
                pointTypeAId = this.pointTypeAId.copy(),
                comparisonFunction = this.comparisonFunction,
                pointTypeBId = this.pointTypeBId.copy()
        ).apply {
            assignNewId(idManager)
        }
    }

    override fun deepRegister(idManager: IdManager) {
        this.addWithCurrentId(idManager)
    }

    override fun deepDelete(idManager: IdManager) {
        this.removeFromIdMap(idManager)
    }
}
