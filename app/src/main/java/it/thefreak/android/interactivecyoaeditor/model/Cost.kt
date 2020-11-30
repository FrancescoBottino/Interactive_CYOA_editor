package it.thefreak.android.interactivecyoaeditor.model

import kotlinx.serialization.Serializable

@Serializable
data class Cost(
        override var id: String? = null,
        override var ordinal: Int? = null,
        override var icon: String? = null,
        var pointTypeId: String? = null,
        var amount: Int? = null,
        var hide: Boolean? = null,
        var modifiers: ArrayList<CostModifier>? = null,
): IdentifiableItem, ListableItem {
    override fun deepCopy(idManager: IdManager): Cost {
        return Cost(
                icon = this.icon.copy(),
                pointTypeId = this.pointTypeId.copy(),
                amount = this.amount,
                hide = this.hide,
                modifiers = this.modifiers.copy(idManager)
        ).apply {
            assignNewId(idManager)
        }
    }

    override fun deepRegister(idManager: IdManager) {
        this.assignNewId(idManager)
        modifiers?.forEach {
            it.deepRegister(idManager)
        }
    }

    override fun deepDelete(idManager: IdManager) {
        this.removeFromIdMap(idManager)
        modifiers?.forEach {
            it.deepDelete(idManager)
        }
    }
}