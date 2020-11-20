package it.thefreak.android.interactivecyoaeditor.model

import kotlinx.serialization.Serializable

@Serializable
data class Choice(
        override var id: String? = null,
        override var ordinal: Int? = null,
        override var name: String? = null,
        override var description: String? = null,
        override var image: String? = null,
        override var icon: String? = null,
        override var style: Style? = null,
        override var hide: Boolean? = null,
        override var requirements: ArrayList<Requirement>? = null,
        var automaticallyActivated: Boolean? = null,
        var buyLimit: Int? = null,
        var costs: ArrayList<Cost>? = null,
        var subChoices: ArrayList<AdventureNode>? = null,
): IdentifiableItem, ListableItem, NarrativeItem, RequirementHolderItem, StylableItem