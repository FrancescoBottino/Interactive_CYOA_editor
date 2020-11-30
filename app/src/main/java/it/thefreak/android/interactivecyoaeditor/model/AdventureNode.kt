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
    override fun deepCopy(idManager: IdManager): AdventureNode {
        return AdventureNode(
                name = this.name.copy(),
                description = this.description.copy(),
                image = this.image.copy(),
                icon = this.icon.copy(),
                style = this.style?.deepCopy(idManager),
                hide = this.hide,
                requirements = this.requirements.copy(idManager),
                nodeSpecificPoints = this.nodeSpecificPoints.copy(idManager),
                hasChoices = this.hasChoices,
                choiceLimit = this.choiceLimit,
                choiceGroupType = this.choiceGroupType,
                choicesList = this.choicesList.copy(idManager)
        ).apply {
            assignNewId(idManager)
        }
    }

    override fun deepRegister(idManager: IdManager) {
        this.addWithCurrentId(idManager)
        requirements?.forEach {
            it.deepRegister(idManager)
        }
        nodeSpecificPoints?.forEach {
            it.deepRegister(idManager)
        }
        choicesList?.forEach {
            it.deepRegister(idManager)
        }
    }

    override fun deepDelete(idManager: IdManager) {
        this.removeFromIdMap(idManager)
        requirements?.forEach {
            it.deepDelete(idManager)
        }
        nodeSpecificPoints?.forEach {
            it.deepDelete(idManager)
        }
        choicesList?.forEach {
            it.deepDelete(idManager)
        }
    }
}