package it.thefreak.android.interactivecyoaeditor.model

import kotlinx.serialization.Serializable

/**
 * TODO:
 *  ADD MORPHING ON SPECIAL-REQUIREMENTS MET (ON SELECTION EX.: CHOICE SELECTION, SELF, MORPHS INTO NON-ACTIVATABLE)
 *  ADD LIST OF CHOICES TO FORCE ACTIVE OR INACTIVE ON SELECTION
 *  LINK MULTIBUY TO A POINTTYPE
 */

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
        var activatable: Boolean? = null,
        var automaticallyActivated: Boolean? = null,
        var conditions: ArrayList<Requirement>? = null,
        var multiBuy: Boolean? = null,
        var buyLimit: Int? = null,
        var costs: ArrayList<Cost>? = null,
        var subNodes: ArrayList<AdventureNode>? = null,
): IdentifiableItem, ListableItem, NarrativeItem, RequirementHolderItem, StylableItem {
    override fun deepCopy(idManager: IdManager): Choice {
        return Choice(
                name = this.name.copy(),
                description = this.description.copy(),
                image = this.image.copy(),
                icon = this.icon.copy(),
                style = this.style?.deepCopy(idManager),
                hide = this.hide,
                requirements = this.requirements.copy(idManager),
                activatable = this.activatable,
                automaticallyActivated = this.automaticallyActivated,
                conditions = this.conditions.copy(idManager),
                multiBuy = this.multiBuy,
                buyLimit = this.buyLimit,
                costs = this.costs.copy(idManager),
                subNodes = this.subNodes.copy(idManager)
        ).apply {
            assignNewId(idManager)
        }
    }

    override fun deepRegister(idManager: IdManager) {
        this.addWithCurrentId(idManager)
        requirements?.forEach {
            it.deepRegister(idManager)
        }
        conditions?.forEach {
            it.deepRegister(idManager)
        }
        costs?.forEach {
            it.deepRegister(idManager)
        }
        subNodes?.forEach {
            it.deepRegister(idManager)
        }
    }

    override fun deepDelete(idManager: IdManager) {
        this.removeFromIdMap(idManager)
        requirements?.forEach {
            it.deepDelete(idManager)
        }
        conditions?.forEach {
            it.deepDelete(idManager)
        }
        costs?.forEach {
            it.deepDelete(idManager)
        }
        subNodes?.forEach {
            it.deepDelete(idManager)
        }
    }
}