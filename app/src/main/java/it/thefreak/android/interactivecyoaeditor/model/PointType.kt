package it.thefreak.android.interactivecyoaeditor.model

import kotlinx.serialization.Serializable

@Serializable
data class PointType(
        override var id: String? = null,
        override var ordinal: Int? = null,
        override var name: String? = null,
        override var description: String? = null,
        override var image: String? = null,
        override var icon: String? = null,
        override var style: Style? = null,
        override var hide: Boolean? = null,
        override var requirements: ArrayList<Requirement>? = null,
        var canGoUnderZero: Boolean? = null,
        var initialAmount: Int? = null,
): IdentifiableItem, ListableItem, NarrativeItem, RequirementHolderItem, StylableItem {
    override fun deepCopy(idManager: IdManager): PointType {
        return PointType(
                name = this.name.copy(),
                description = this.description.copy(),
                image = this.image.copy(),
                icon = this.icon.copy(),
                style = this.style?.deepCopy(idManager),
                hide = this.hide,
                requirements = this.requirements.copy(idManager),
                canGoUnderZero = this.canGoUnderZero,
                initialAmount = this.initialAmount
        ).apply {
            assignNewId(idManager)
        }
    }

    override fun deepRegister(idManager: IdManager) {
        this.addWithCurrentId(idManager)
        requirements?.forEach {
            it.deepRegister(idManager)
        }
    }

    override fun deepDelete(idManager: IdManager) {
        this.removeFromIdMap(idManager)
        requirements?.forEach {
            it.deepDelete(idManager)
        }
    }
}