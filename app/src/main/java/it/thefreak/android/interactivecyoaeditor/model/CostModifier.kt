package it.thefreak.android.interactivecyoaeditor.model

import kotlinx.serialization.Serializable

@Serializable
data class CostModifier(
        override var hide: Boolean? = null,
        override var requirements: ArrayList<Requirement>? = null,
        var amount: Int? = null,
): RequirementHolderItem