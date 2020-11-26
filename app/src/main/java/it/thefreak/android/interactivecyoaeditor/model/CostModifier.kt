package it.thefreak.android.interactivecyoaeditor.model

import kotlinx.serialization.Serializable

@Serializable
data class CostModifier(
        override var id: String? = null,
        override var ordinal: Int?= null,
        override var icon: String?= null,
        override var hide: Boolean? = null,
        override var requirements: ArrayList<Requirement>? = null,
        var amount: Int? = null,
): IdentifiableItem, ListableItem, RequirementHolderItem