package it.thefreak.android.interactivecyoaeditor.views.itemselectordialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import it.thefreak.android.interactivecyoaeditor.R
import it.thefreak.android.interactivecyoaeditor.databinding.SingleItemSelectorDialogBinding
import it.thefreak.android.interactivecyoaeditor.onClick

class SingleItemSelectorDialogBuilder<T> (val ctx: Context) {

    private val view: View = LayoutInflater.from(ctx)
            .inflate(R.layout.single_item_selector_dialog, null)

    private val dialogBuilder: MaterialAlertDialogBuilder = MaterialAlertDialogBuilder(ctx)
            .setView(view)

    private val binding: SingleItemSelectorDialogBinding = SingleItemSelectorDialogBinding
            .bind(view)

    private lateinit var createdDialog: AlertDialog

    @LayoutRes private var layoutRes: Int? = null
    private lateinit var itemsList: Collection<T>
    private lateinit var viewBinder: ViewBinder<T>
    private lateinit var clickListener: (T, AlertDialog) -> Unit
    private val internalListener = { item: T ->
        clickListener(item, createdDialog)
    }

    fun setTitle(@StringRes titleId: Int): SingleItemSelectorDialogBuilder<T> {
        dialogBuilder.setTitle(titleId)
        return this
    }

    fun setTitle(title: CharSequence?): SingleItemSelectorDialogBuilder<T> {
        dialogBuilder.setTitle(title)
        return this
    }

    private class Binder<T>(
            val content: T,
            override val layoutRes: Int,
            val viewBinder: ViewBinder<T>,
            val clickListener: (T) -> Unit,
    ): AbstractItem<RecyclerView.ViewHolder>() {
        override val type: Int
            get() = R.id.single_item_selector_dialog_id

        override fun getViewHolder(v: View): ViewHolder<T> = ViewHolder(v)

        class ViewHolder<T>(val v: View): FastAdapter.ViewHolder<Binder<T>>(v) {
            override fun bindView(item: Binder<T>, payloads: List<Any>) {
                item.viewBinder.bindView(item.content, v)
                v.onClick {
                    item.clickListener(item.content)
                }
            }

            override fun unbindView(item: Binder<T>) {
                item.viewBinder.unbindView(item.content, v)
                v.onClick(null)
            }
        }
    }

    interface ViewBinder<T> {
        fun bindView(item: T, view: View)
        fun unbindView(item: T, view: View)
    }

    fun setItemsList(items: Collection<T>, viewBinder: ViewBinder<T>): SingleItemSelectorDialogBuilder<T> {
        this.itemsList = items
        this.viewBinder = viewBinder
        return this
    }

    fun setOnSelection(clickListener: (T, AlertDialog) -> Unit): SingleItemSelectorDialogBuilder<T> {
        this.clickListener = clickListener
        return this
    }

    fun setItemLayout(@LayoutRes layoutRes: Int): SingleItemSelectorDialogBuilder<T> {
        this.layoutRes = layoutRes
        return this
    }

    fun create(): AlertDialog {
        if(layoutRes == null)
            throw Exception("can't build SingleItemSelectorDialogBuilder, no layout resource provided")
        if(!(this::itemsList.isInitialized))
            throw Exception("can't build SingleItemSelectorDialogBuilder, no items list provided")
        if(!(this::itemsList.isInitialized))
            throw Exception("can't build SingleItemSelectorDialogBuilder, no view binding defined")
        if(!(this::itemsList.isInitialized))
            throw Exception("can't build SingleItemSelectorDialogBuilder, no click listener defined")

        val itemAdapter = ItemAdapter<Binder<T>>()
        itemAdapter.add(
                itemsList.map {
                    Binder(it, layoutRes!!, viewBinder, internalListener)
                }
        )
        val itemsListAdapter = FastAdapter.with(itemAdapter)
        binding.itemsList.layoutManager = LinearLayoutManager(ctx)
        binding.itemsList.adapter = itemsListAdapter

        createdDialog = dialogBuilder.setCancelable(true).create()

        return createdDialog
    }

    fun show() {
        create().show()
    }
}