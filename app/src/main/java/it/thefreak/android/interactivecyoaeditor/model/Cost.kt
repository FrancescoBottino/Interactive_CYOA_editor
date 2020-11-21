package it.thefreak.android.interactivecyoaeditor.model

import kotlinx.serialization.Serializable

@Serializable
data class Cost(
        override var ordinal: Int? = null,
        override var icon: String? = null,
        var pointTypeId: String? = null,
        var amount: Int? = null,
        var hide: Boolean? = null,
        var modifiers: ArrayList<CostModifier>? = null,
): ListableItem