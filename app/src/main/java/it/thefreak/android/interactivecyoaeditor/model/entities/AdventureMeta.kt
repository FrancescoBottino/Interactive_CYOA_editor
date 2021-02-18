package it.thefreak.android.interactivecyoaeditor.model.entities

import it.thefreak.android.interactivecyoaeditor.model.itemtypes.IdentifiableItem
import it.thefreak.android.interactivecyoaeditor.model.itemtypes.ListableItem
import kotlinx.serialization.Serializable

@Serializable
data class AdventureMeta(
    override var id: String? = null,
    override var icon: String? = null,
    override var ordinal: Int? = null,
    var name: String? = null,
    var description: String? = null,
    var version: String? = null,
    var author: String? = null,
    var engineVersion: String? = null,
): ListableItem, IdentifiableItem {
}