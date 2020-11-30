package it.thefreak.android.interactivecyoaeditor.ui.editor.components

import it.thefreak.android.interactivecyoaeditor.model.IdManager
import it.thefreak.android.interactivecyoaeditor.model.ListableItem
import it.thefreak.android.interactivecyoaeditor.model.assignNewId
import it.thefreak.android.interactivecyoaeditor.model.init
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
        item.deepDelete(idManager)
        container.init().remove(item)
        return true
    }

    override fun onItemClick(item: T) {
        clickListener(item)
    }

    override fun onItemCopy(item: T): T {
        return (item.deepCopy(idManager) as T).apply {
            container.init().add(this)
        }
    }

    override fun onNewItem(adder: (T)->Unit) {
        factory()?.apply {
            assignNewId(idManager)
            container.init().add(this)
            adder(this)
        }
    }
}