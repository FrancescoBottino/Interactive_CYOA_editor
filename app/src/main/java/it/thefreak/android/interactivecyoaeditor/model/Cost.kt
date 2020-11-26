package it.thefreak.android.interactivecyoaeditor.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class Cost(
        override var id: String? = null,
        override var ordinal: Int? = null,
        override var icon: String? = null,
        var pointTypeId: String? = null,
        @Transient
        @property:ItemLinkField
        var pointType: PointType? = null,
        var amount: Int? = null,
        var hide: Boolean? = null,
        var modifiers: ArrayList<CostModifier>? = null,
): IdentifiableItem, ListableItem