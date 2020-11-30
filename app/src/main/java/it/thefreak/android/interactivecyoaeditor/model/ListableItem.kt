package it.thefreak.android.interactivecyoaeditor.model

interface ListableItem: IdentifiableItem {
    var ordinal: Int?
    var icon: String?
}