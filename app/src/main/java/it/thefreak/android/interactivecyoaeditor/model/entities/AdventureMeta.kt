package it.thefreak.android.interactivecyoaeditor.model.entities

import android.net.Uri
import it.thefreak.android.interactivecyoaeditor.model.itemtypes.ListableItem
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class AdventureMeta(
    override var icon: String? = null,
    override var ordinal: Int? = null,
    var name: String? = null,
    var description: String? = null,
    var version: String? = null,
    var author: String? = null,
    var engineVersion: String? = null,
    @Transient var uri: Uri? = null,
): ListableItem