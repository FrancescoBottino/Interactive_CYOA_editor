package it.thefreak.android.interactivecyoaeditor.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("AdditiveCostModifier")
data class AdditiveCostModifier(
        override var id: String? = null,
        override var ordinal: Int? = null,
        override var icon: String? = null,
        override var hide: Boolean? = null,
        override var requirements: ArrayList<Requirement>? = null,
        var amountInt: Int? = null,
): CostModifier {
    @SerialName("AdditiveCostModifierType")
    override val type: CostModifierType = CostModifierType.ADDITIVE
    override fun deepCopy(idManager: IdManager): AdditiveCostModifier {
        return AdditiveCostModifier(
                icon = this.icon.copy(),
                hide = this.hide,
                requirements = this.requirements.copy(idManager),
                amountInt = this.amountInt,
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