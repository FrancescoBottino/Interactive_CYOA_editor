package it.thefreak.android.interactivecyoaeditor.ui.editor.components

import it.thefreak.android.interactivecyoaeditor.model.*
import it.thefreak.android.interactivecyoaeditor.views.itemslisteditor.ItemsListEditorItemListener
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty0

class ItemsListEditorListenerFactory<T: ListableItem>(
        private val type: KClass<T>,
        private val factory: ()->T?,
        private val idManager: IdManager,
        private val container: KMutableProperty0<ArrayList<T>?>,
        private val clickListener: (T) -> Unit,
): ItemsListEditorItemListener<T> {
    override fun onItemDelete(item: T): Boolean {
        item.deepDeleteItem(type, idManager, container.get())
        return true
    }

    override fun onItemClick(item: T) {
        clickListener(item)
    }

    override fun onItemCopy(item: T): T {
        return item.deepCopyItem(type, idManager).apply {
            container.init().add(this)
        }
    }

    override fun onNewItem(): T? {
        return factory()?.apply {
            if(this is IdentifiableItem) {
                assignNewId(idManager)
            }
            container.init().add(this)
        }
    }
}