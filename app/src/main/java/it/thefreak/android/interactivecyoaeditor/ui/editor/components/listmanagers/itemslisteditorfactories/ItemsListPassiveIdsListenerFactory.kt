package it.thefreak.android.interactivecyoaeditor.ui.editor.components.listmanagers.itemslisteditorfactories

import it.thefreak.android.interactivecyoaeditor.model.init
import it.thefreak.android.interactivecyoaeditor.model.itemtypes.ListManageableItem
import it.thefreak.android.interactivecyoaeditor.ui.editor.components.chooserdialog.Chooser
import it.thefreak.android.interactivecyoaeditor.views.itemslisteditor.ItemsListEditorItemListener
import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty0

class ItemsListPassiveIdsListenerFactory<T: ListManageableItem>(
        private val type: KClass<T>,
        private val chooser: Chooser<T>,
        private val container: KMutableProperty0<HashSet<String>?>,
): ItemsListEditorItemListener<T> {
    override fun onItemDelete(item: T): Boolean {
        container.init().remove(item.id)
        return true
    }

    override fun onItemClick(item: T) {}

    override fun onItemCopy(item: T): T? = null

    override fun onNewItem(adder: (T)->Unit) {
        chooser.choose(currentlySelected = container.get()) { chosen ->
            container.init().add(chosen.id!!)
            adder(chosen)
        }
    }
}