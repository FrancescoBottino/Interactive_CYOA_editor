package it.thefreak.android.interactivecyoaeditor.model.entities

import it.thefreak.android.interactivecyoaeditor.model.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("ChoiceSelectionRequirement")
data class ChoiceSelectionRequirement(
    override var id: String? = null,
    override var ordinal: Int? = null,
    override var icon: String? = null,
    var groupingFunction: GroupingFunction? = null,
    var choicesIds: HashSet<String>? = null,
) : Requirement {
    @SerialName("ChoiceSelectionRequirementType")
    override val type: RequirementType = RequirementType.CHOICE_SELECTION
    override fun deepCopy(idManager: IdManager): ChoiceSelectionRequirement {
        return ChoiceSelectionRequirement(
                icon = this.icon.copy(),
                groupingFunction = this.groupingFunction,
                choicesIds = this.choicesIds.copy()
        ).apply {
            assignNewId(idManager)
        }
    }

    override fun deepRegister(idManager: IdManager) {
        this.addWithCurrentId(idManager)
    }

    override fun deepDelete(idManager: IdManager) {
        this.removeFromIdMap(idManager)
    }
}
