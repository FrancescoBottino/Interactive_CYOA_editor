package it.thefreak.android.interactivecyoaeditor.views.itemselectordialog

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import it.thefreak.android.interactivecyoaeditor.R
import it.thefreak.android.interactivecyoaeditor.databinding.SingleItemSelectorDialogBinding
import it.thefreak.android.interactivecyoaeditor.hide
import it.thefreak.android.interactivecyoaeditor.onClick


class SingleItemSelectorDialogBuilder<T>(val ctx: Context) {

    private val dialogBuilder: MaterialAlertDialogBuilder = MaterialAlertDialogBuilder(ctx)
    private lateinit var createdDialog: AlertDialog

    @LayoutRes private var layoutRes: Int? = null
    private lateinit var itemsList: Collection<T>
    private lateinit var viewBinder: ViewBinder<T>
    private lateinit var itemClickListener: (T, AlertDialog) -> Unit
    private val internalItemClickListener = { item: T ->
        itemClickListener(item, createdDialog)
    }

    private var hasSearchBar: Boolean = false
    private var filterPredicate: ((T, CharSequence?) -> Boolean)? = null
    private val internalFilterPredicate = { binder: Binder<T>, query: CharSequence? ->
        filterPredicate?.invoke(binder.content, query) ?: true
    }

    private var hasNegativeButton: Boolean = false
    private var negativeButtonName: String? = null
    private var negativeButtonListener: ((AlertDialog) -> Unit)? = null
    private val internalNegativeButtonListener: (DialogInterface?, Int) -> Unit = { d: DialogInterface?, w: Int ->
        negativeButtonListener?.invoke(d as AlertDialog)
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
                item.viewBinder.bindView(item.content, v).onClick {
                    item.clickListener(item.content)
                }
            }

            override fun unbindView(item: Binder<T>) {
                item.viewBinder.unbindView(item.content, v).onClick(null)
            }
        }
    }

    interface ViewBinder<T> {
        fun bindView(item: T, view: View): View
        fun unbindView(item: T, view: View): View
    }

    fun setItemsList(items: Collection<T>, viewBinder: ViewBinder<T>): SingleItemSelectorDialogBuilder<T> {
        this.itemsList = items
        this.viewBinder = viewBinder
        return this
    }

    fun setOnSelection(clickListener: (T, AlertDialog) -> Unit): SingleItemSelectorDialogBuilder<T> {
        this.itemClickListener = clickListener
        return this
    }

    fun setItemLayout(@LayoutRes layoutRes: Int): SingleItemSelectorDialogBuilder<T> {
        this.layoutRes = layoutRes
        return this
    }

    fun setFilterPredicate(filterPredicate: (T, CharSequence?) -> Boolean): SingleItemSelectorDialogBuilder<T> {
        hasSearchBar = true
        this.filterPredicate = filterPredicate
        return this
    }

    fun setNegativeButton(label: String, negativeButtonListener: (AlertDialog) -> Unit): SingleItemSelectorDialogBuilder<T> {
        hasNegativeButton = true
        this.negativeButtonListener = negativeButtonListener
        this.negativeButtonName = label
        return this
    }

    fun setNegativeButton(@StringRes labelRes: Int, negativeButtonListener: (AlertDialog) -> Unit): SingleItemSelectorDialogBuilder<T> {
        return setNegativeButton(ctx.getString(labelRes), negativeButtonListener)
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

        val view: View = LayoutInflater.from(ctx)
                .inflate(R.layout.single_item_selector_dialog, null)
        val binding: SingleItemSelectorDialogBinding = SingleItemSelectorDialogBinding
                .bind(view)

        createdDialog = dialogBuilder.apply {
            if(hasNegativeButton) {
                setNegativeButton(negativeButtonName, internalNegativeButtonListener)
            }
            setView(view)
            setCancelable(true)
        }.create()

        val adapter = FastItemAdapter<Binder<T>>().apply {
            add(itemsList.map { Binder(it, layoutRes!!, viewBinder, internalItemClickListener) })
            if(hasSearchBar) {
                itemFilter.filterPredicate = internalFilterPredicate
            }
        }

        with(binding) {
            with(itemsList) {
                this.layoutManager = LinearLayoutManager(ctx)
                this.adapter = adapter
            }
            with(searchbar) {
                setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean = true
                    override fun onQueryTextChange(newText: String?): Boolean {
                        adapter.filter(newText)
                        return true
                    }
                })
                if(!hasSearchBar) {
                    hide()
                }
            }
        }

        return createdDialog
    }

    fun show() {
        create().show()
    }
}