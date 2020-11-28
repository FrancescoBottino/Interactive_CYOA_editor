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
}
