package it.thefreak.android.interactivecyoaeditor.ui.editor.binders

interface ListItemListener<T> {
    fun onItemDelete(item: T)
    fun onItemClick(item: T)
    fun onItemCopy(item: T)
}