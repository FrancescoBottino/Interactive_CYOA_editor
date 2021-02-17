package it.thefreak.android.interactivecyoaeditor.model

interface PolymorphicListableItem<T>: ListableItem {
    val type: T
}