package it.thefreak.android.interactivecyoaeditor.ui.editor.components.listmanagers.itemslisteditorfactories

import com.google.android.material.dialog.MaterialAlertDialogBuilder
import it.thefreak.android.interactivecyoaeditor.model.IdManager
import it.thefreak.android.interactivecyoaeditor.model.PolymorphicListableItem
import it.thefreak.android.interactivecyoaeditor.model.assignNewId
import it.thefreak.android.interactivecyoaeditor.model.init
import it.thefreak.android.interactivecyoaeditor.views.itemslisteditor.ItemsListEditorItemListener
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty0

class ItemsListFormPickerFactory<T: Any, P: PolymorphicListableItem<T>>(
        private val type: KClass<P>,
        private val idManager: IdManager,
        private val container: KMutableProperty0<ArrayList<P>?>,
        private val clickListener: (P) -> Unit,
        private val dialogMaker: MaterialAlertDialogBuilder,
        private val bindings: Map<T, Coupler<P>>,
): ItemsListEditorItemListener<P> {
    data class Coupler<P>(
            val label: String,
            val copier: (P, IdManager)->P,
            val factory: ()->P,
    )

    override fun onItemDelete(item: P): Boolean {
        item.deepDelete(idManager)
        container.init().remove(item)
        return true
    }

    override fun onItemClick(item: P) {
        clickListener(item)
    }

    override fun onItemCopy(item: P): P {
        return bindings[item.type]!!.copier(item, idManager).apply {
            container.init().add(this)
        }
    }

    override fun onNewItem(adder: (P) -> Unit) {
        val items = bindings.values.map { it.label }.toTypedArray()
        dialogMaker.setItems(items) { dialog, which ->
                    val type = bindings.keys.toList()[which]
                    bindings[type]?.factory?.invoke()?.let {
                        it.assignNewId(idManager)
                        container.init().add(it)
                        adder(it)
                    }
                }.setCancelable(true).show()
    }
}