package it.thefreak.android.interactivecyoaeditor.ui.editor.components.chooserdialog

import android.content.Context
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import it.thefreak.android.interactivecyoaeditor.model.IdManager
import it.thefreak.android.interactivecyoaeditor.model.IdentifiableItem
import it.thefreak.android.interactivecyoaeditor.views.itemselectordialog.SingleItemSelectorDialogBuilder
import kotlin.reflect.KClass

open class Chooser<T: IdentifiableItem>(
        private val ctx: Context,
        private val type: KClass<T>,
        @StringRes private val name: Int,
        @LayoutRes private val layout: Int,
        private val viewBinder: SingleItemSelectorDialogBuilder.ViewBinder<T>,
        private val idManager: IdManager,
) {
    fun choose(currentlySelected: Set<String>? = null, onChoose: (T)->Unit) {
        val items = idManager.idMap.values
            .filter { it::class == type && !(currentlySelected?.contains(it.id!!)?:false) } as List<T>

        SingleItemSelectorDialogBuilder<T>(ctx)
                .setTitle(name)
                .setItemLayout(layout)
                .setItemsList(items, viewBinder)
                .setOnSelection { item, dialog ->
                    dialog.dismiss()
                    onChoose(item)
                }

    }
}