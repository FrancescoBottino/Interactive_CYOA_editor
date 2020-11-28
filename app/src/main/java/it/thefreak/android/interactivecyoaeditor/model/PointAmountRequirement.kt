package it.thefreak.android.interactivecyoaeditor.model

import kotlinx.serialization.Serializable

@Serializable
data class PointAmountRequirement(
        override var id: String? = null,
        override var ordinal: Int? = null,
        override var icon: String? = null,
        var pointTypeId: String? = null,
        var comparisonFunction: ComparisonFunction? = null,
        var amount: Int? = null,
): Requirement {
    override val type: RequirementType = RequirementType.POINT_AMOUNT
}
