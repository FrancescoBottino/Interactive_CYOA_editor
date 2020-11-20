package it.thefreak.android.interactivecyoaeditor.model

import kotlinx.serialization.Serializable

@Serializable
data class AdventureNode(
        override var id: String? = null,
        override var ordinal: Int? = null,
        override var name: String? = null,
        override var description: String? = null,
        override var image: String? = null,
        override var icon: String? = null,
        override var style: Style? = null,
        override var hide: Boolean? = null,
        override var requirements: ArrayList<Requirement>? = null,
        var nodeSpecificPoints: ArrayList<PointType>? = null,
        var hasChoices: Boolean? = null,
        var choiceLimit: Int? = null,
        var choiceGroupType: ChoicesGroupType? = null,
        var choicesList: ArrayList<Choice>? = null,
): IdentifiableItem, ListableItem, NarrativeItem, RequirementHolderItem, StylableItem {
    enum class ChoicesGroupType {
        OPTIONAL, REQUIRED
    }
}