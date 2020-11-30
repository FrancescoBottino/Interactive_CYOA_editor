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
    //TODO what do to with this class?

    override var id: String? = null

    override fun deepCopy(idManager: IdManager): IdentifiableItem {
        TODO("Not yet implemented")
    }

    override fun deepRegister(idManager: IdManager) {
        TODO("Not yet implemented")
    }

    override fun deepDelete(idManager: IdManager) {
        TODO("Not yet implemented")
    }

    constructor(adv: Adventure, uri: Uri): this(
            null,
            null,
            adv.name,
            adv.version,
            adv.author,
            uri,
    )
}