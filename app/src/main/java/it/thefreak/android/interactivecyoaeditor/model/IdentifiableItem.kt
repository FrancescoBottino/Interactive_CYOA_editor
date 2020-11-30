package it.thefreak.android.interactivecyoaeditor.model

interface IdentifiableItem: Item {
    var id: String?

    fun deepCopy(idManager: IdManager): IdentifiableItem
    fun deepRegister(idManager: IdManager)
    fun deepDelete(idManager: IdManager)
}