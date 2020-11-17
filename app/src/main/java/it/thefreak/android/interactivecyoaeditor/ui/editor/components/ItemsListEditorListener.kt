package it.thefreak.android.interactivecyoaeditor.ui.editor.components

interface ItemsListEditorListener<T> {
    fun onItemDelete(item: T)
    fun onItemClick(item: T)
    fun onItemCopy(item: T): T
    fun onNewItem(): T
}