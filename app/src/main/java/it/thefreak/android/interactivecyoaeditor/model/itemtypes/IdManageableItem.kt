package it.thefreak.android.interactivecyoaeditor.model.itemtypes

import it.thefreak.android.interactivecyoaeditor.model.IdManager

interface IdManageableItem: IdentifiableItem {
    fun deepCopy(idManager: IdManager): IdManageableItem
    fun deepRegister(idManager: IdManager)
    fun deepDelete(idManager: IdManager)
}