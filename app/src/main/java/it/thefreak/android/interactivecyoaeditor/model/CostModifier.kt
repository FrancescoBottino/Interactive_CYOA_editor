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
): IdentifiableItem, ListableItem, RequirementHolderItem {
    override fun deepCopy(idManager: IdManager): CostModifier {
        return CostModifier(
                icon = this.icon.copy(),
                hide = this.hide,
                requirements = this.requirements.copy(idManager),
                amount = this.amount
        ).apply{
            assignNewId(idManager)
        }
    }

    override fun deepRegister(idManager: IdManager) {
        this.assignNewId(idManager)
        requirements?.forEach {
            deepRegister(idManager)
        }
    }

    override fun deepDelete(idManager: IdManager) {
        this.removeFromIdMap(idManager)
        requirements?.forEach {
            it.deepDelete(idManager)
        }
    }
}