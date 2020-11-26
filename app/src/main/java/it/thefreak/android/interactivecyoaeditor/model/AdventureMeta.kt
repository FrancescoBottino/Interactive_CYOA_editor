package it.thefreak.android.interactivecyoaeditor.model

import android.net.Uri
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class AdventureMeta(
        override var icon: String? = null,
        override var ordinal: Int? = null,
        var name: String? = null,
        var version: String? = null,
        var author: String? = null,
        @Transient var adventureUri: Uri? = null,
): ListableItem {
    constructor(adv: Adventure, uri: Uri): this(
            null,
            null,
            adv.name,
            adv.version,
            adv.author,
            uri,
    )
}