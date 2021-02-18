package it.thefreak.android.interactivecyoaeditor.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("MultiplicativeCostModifier")
data class MultiplicativeCostModifier(
        override var id: String? = null,
        override var ordinal: Int? = null,
        override var icon: String? = null,
        override var hide: Boolean? = null,
        override var requirements: ArrayList<Requirement>? = null,
        var amountDouble: Double? = null,
): CostModifier {
    @SerialName("MultiplicativeCostModifierType")
    override val type: CostModifierType = CostModifierType.MULTIPLICATIVE
    override fun deepCopy(idManager: IdManager): MultiplicativeCostModifier {
        return MultiplicativeCostModifier(
                icon = this.icon.copy(),
                hide = this.hide,
                requirements = this.requirements.copy(idManager),
                amountDouble = this.amountDouble,
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