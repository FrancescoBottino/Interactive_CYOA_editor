package it.thefreak.android.interactivecyoaeditor.model.entities

import it.thefreak.android.interactivecyoaeditor.model.IdManager
import it.thefreak.android.interactivecyoaeditor.model.itemtypes.IdManageableItem
import kotlinx.serialization.Serializable

@Serializable
data class Style(
    //TODO OTHER OPTIONS
    override var id: String? = null,
    var bgColorCode: String? = null,
    var textColorCode: String? = null,
    //var font: Font? = null
): IdManageableItem {
    override fun deepCopy(idManager: IdManager): Style {
        TODO("Not yet implemented")
    }

    override fun deepRegister(idManager: IdManager) {
        TODO("Not yet implemented")
    }

    override fun deepDelete(idManager: IdManager) {
        TODO("Not yet implemented")
    }
}