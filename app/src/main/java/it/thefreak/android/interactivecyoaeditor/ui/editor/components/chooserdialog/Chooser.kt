package it.thefreak.android.interactivecyoaeditor.ui.editor.components.chooserdialog

import android.content.Context
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import it.thefreak.android.interactivecyoaeditor.R
import it.thefreak.android.interactivecyoaeditor.model.IdManager
import it.thefreak.android.interactivecyoaeditor.model.itemtypes.IdManageableItem
import it.thefreak.android.interactivecyoaeditor.views.itemselectordialog.SingleItemSelectorDialogBuilder
import kotlin.reflect.KClass

abstract class Chooser<T: IdManageableItem>(
        private val ctx: Context,
        private val type: KClass<T>,
        private val idManager: IdManager,
) {
    fun choose(currentlySelected: Set<String>? = null, onChoose: (T)->Unit) {
        val items = generateItemsList(currentlySelected)
                .additionalFiltering()
                .sorting()

        SingleItemSelectorDialogBuilder<T>(ctx).apply {
            setTitle(name)
            setItemLayout(layout)
            setItemsList(items, viewBinder)
            if(isSearchFilteringEnabled)
                setFilterPredicate(this@Chooser::searchFilteringPredicate)
            if(isNegativeButtonEnabled)
                setNegativeButton(R.string.chooser_dialog_cancel, this@Chooser::negativeButtonAction)

            setOnSelection { item, dialog ->
                dialog.dismiss()
                onChoose(item)
            }.show()
        }
    }

    @Suppress("UNCHECKED_CAST")
    protected fun generateItemsList(currentlySelected: Set<String>? = null): List<T> {
        return idManager.findByType(type).filter { (id, _) ->
            !(currentlySelected?.contains(id)?:false)
        }.values.toList()
    }

    protected open fun List<T>.additionalFiltering(): List<T> {
        return this
    }

    protected open fun List<T>.sorting(): List<T> {
        return this
    }

    protected abstract val name: Int
    @StringRes get
    protected abstract val layout: Int
    @LayoutRes get

    protected abstract val isSearchFilteringEnabled: Boolean
    protected open fun searchFilteringPredicate(item: T, query: CharSequence?): Boolean {
        return true
    }

    protected abstract val isNegativeButtonEnabled: Boolean
    protected open fun negativeButtonAction(dialog: AlertDialog) {
        dialog.dismiss()
    }

    protected abstract val viewBinder: SingleItemSelectorDialogBuilder.ViewBinder<T>
}