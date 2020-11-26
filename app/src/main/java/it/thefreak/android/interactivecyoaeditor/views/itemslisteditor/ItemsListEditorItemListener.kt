package it.thefreak.android.interactivecyoaeditor.views.itemslisteditor

interface ItemsListEditorItemListener<T> {
    fun onItemDelete(item: T): Boolean
    fun onItemClick(item: T)
    fun onItemCopy(item: T): T?
    fun onNewItem(): T?
}