package it.thefreak.android.interactivecyoaeditor.model

interface RequirementHolderItem: Item {
    var hide: Boolean?
    var requirements: ArrayList<Requirement>?
}