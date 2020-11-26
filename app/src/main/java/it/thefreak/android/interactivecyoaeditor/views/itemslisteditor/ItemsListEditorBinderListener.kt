package it.thefreak.android.interactivecyoaeditor.views.itemslisteditor

interface ItemsListEditorBinderListener<T> {
    fun onItemDelete(item: T)
    fun onItemClick(item: T)
    fun onItemCopy(item: T)
}