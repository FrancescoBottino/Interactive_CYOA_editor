package it.thefreak.android.interactivecyoaeditor.model

interface Requirement: IdentifiableItem, ListableItem {
    val type: RequirementType
}