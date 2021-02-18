package it.thefreak.android.interactivecyoaeditor.model.entities

import it.thefreak.android.interactivecyoaeditor.model.IdManager
import it.thefreak.android.interactivecyoaeditor.model.itemtypes.ListableItem
import it.thefreak.android.interactivecyoaeditor.model.itemtypes.NarrativeItem
import it.thefreak.android.interactivecyoaeditor.model.itemtypes.StylableItem
import it.thefreak.android.interactivecyoaeditor.utils.Exclude
import kotlinx.serialization.Serializable

@Serializable
data class Adventure(
    override var ordinal: Int? = null,
    override var name: String? = null,
    override var description: String? = null,
    override var icon: String? = null,
    @Exclude
    override var image: String? = null,
    @Exclude
    override var style: Style? = null,
    var version: String? = null,
    var author: String? = null,
    @Exclude
    var initialPoints: ArrayList<PointType>? = null,
    @Exclude
    var adventureNodesList: ArrayList<AdventureNode>? = null,
    var engineVersion: String? = null,
): ListableItem, NarrativeItem, StylableItem {
    fun getMetadata(): AdventureMeta {
        return AdventureMeta(icon, ordinal, name, description, version, author, engineVersion)
    }

    fun deepRegister(idManager: IdManager) {
        initialPoints?.forEach {
            it.deepRegister(idManager)
        }
        adventureNodesList?.forEach {
            it.deepRegister(idManager)
        }
    }
}