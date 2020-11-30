package it.thefreak.android.interactivecyoaeditor.model

interface Requirement: IdentifiableItem, ListableItem {
    val type: RequirementType
    override fun deepCopy(idManager: IdManager): Requirement
}