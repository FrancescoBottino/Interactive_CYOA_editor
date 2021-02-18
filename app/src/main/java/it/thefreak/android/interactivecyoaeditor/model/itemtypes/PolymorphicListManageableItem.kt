package it.thefreak.android.interactivecyoaeditor.model.itemtypes

interface PolymorphicListManageableItem<T>: ListManageableItem {
    val type: T
}