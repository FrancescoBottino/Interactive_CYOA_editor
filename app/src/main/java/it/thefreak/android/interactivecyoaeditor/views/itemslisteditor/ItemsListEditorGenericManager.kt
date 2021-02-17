package it.thefreak.android.interactivecyoaeditor.views.itemslisteditor

import android.content.Context
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import com.mikepenz.fastadapter.drag.ItemTouchCallback
import com.mikepenz.fastadapter.drag.SimpleDragCallback
import com.mikepenz.fastadapter.utils.DragDropUtil
import com.mikepenz.itemanimators.AlphaInAnimator
import it.thefreak.android.interactivecyoaeditor.hide
import it.thefreak.android.interactivecyoaeditor.model.ListableItem
import it.thefreak.android.interactivecyoaeditor.onClick
import it.thefreak.android.interactivecyoaeditor.show


abstract class ItemsListEditorGenericManager<T : ListableItem, B : ItemsListEditorGenericBinder<T, B>>(
        ctx: Context,
        itemsListEditorView: ItemsListEditorView,
        val binderFactory: (T, ItemsListEditorBinderListener<B>) -> B,
        val itemsListActionsListener: ItemsListEditorItemListener<T>
) {
    private var collapsed: Boolean = false

    private val itemsListAdapter: FastItemAdapter<B> = FastItemAdapter()
    private val recyclerView = itemsListEditorView.binding.recyclerView
    private val collapsedViewMore = itemsListEditorView.binding.collapsedViewMore
    private val emptyListPlaceholder = itemsListEditorView.binding.emptyListPlaceholder

    private val addButton = itemsListEditorView.binding.addButton
    private val collapseButton = itemsListEditorView.binding.collapseButton

    private val itemsListTouchHelper by lazy {
        ItemTouchHelper(
                SimpleDragCallback(
                        object : ItemTouchCallback {
                            override fun itemTouchDropped(oldPosition: Int, newPosition: Int) {
                                super.itemTouchDropped(oldPosition, newPosition)
                                updatePositions()
                            }

                            override fun itemTouchStartDrag(viewHolder: RecyclerView.ViewHolder) {
                                super.itemTouchStartDrag(viewHolder)
                            }

                            override fun itemTouchOnMove(oldPosition: Int, newPosition: Int): Boolean {
                                DragDropUtil.onMove(itemsListAdapter.itemAdapter, oldPosition, newPosition)
                                return true
                            }
                        }
                )
        )
    }

    private val binderActionsListener = object : ItemsListEditorBinderListener<B> {
        override fun onItemDelete(item: B) {
            if ( itemsListActionsListener.onItemDelete(item.content) ) {
                itemsListAdapter.apply {
                    remove(getAdapterPosition(item))
                }
                updatePositions()
                updateVisibleContent()
            }
        }

        override fun onItemClick(item: B) {
            itemsListActionsListener.onItemClick(item.content)
        }

        override fun onItemCopy(item: B) {
            itemsListActionsListener.onItemCopy(item.content)?.let { copiedItem ->
                makeBinder(copiedItem).let { copiedItemBinder ->
                    itemsListAdapter.apply {
                        add(getAdapterPosition(item) + 1, copiedItemBinder)
                        updatePositions()
                        updateVisibleContent()
                    }
                }
            }
        }
    }

    private fun updatePositions() {
        itemsListAdapter.adapterItems.forEach {
            it.content.ordinal = itemsListAdapter.adapterItems.indexOf(it) + 1
        }
    }

    private fun updateVisibleContent() {
        val isEmpty = (itemsListAdapter.itemCount <= 0)
        if(collapsed) {
            recyclerView.hide()
            emptyListPlaceholder.hide()
            collapsedViewMore.show()
        } else {
            if(isEmpty) {
                emptyListPlaceholder.show()
                recyclerView.hide()
            } else {
                recyclerView.show()
                emptyListPlaceholder.hide()
            }
            collapsedViewMore.hide()
        }
    }

    private fun collapse() {
        if(collapsed) {
            collapseButton.animate().rotation(0F).interpolator = AccelerateDecelerateInterpolator()
        } else {
            collapseButton.animate().rotation(-90F).interpolator = AccelerateDecelerateInterpolator()
        }
        collapsed = !collapsed
        updateVisibleContent()
    }

    private fun addItem(newItem: T) {
        newItem.ordinal = itemsListAdapter.itemCount + 1
        itemsListAdapter.add(makeBinder(newItem))

        updateVisibleContent()
    }

    init {
        recyclerView.layoutManager = LinearLayoutManager(ctx)
        recyclerView.itemAnimator = AlphaInAnimator()
        itemsListTouchHelper.attachToRecyclerView(recyclerView)
        recyclerView.adapter = itemsListAdapter

        updateVisibleContent()

        addButton.onClick {
            itemsListActionsListener.onNewItem(::addItem)
        }

        collapseButton.onClick {
            collapse()
        }
        collapsedViewMore.onClick {
            collapse()
        }
    }

    fun set(list: List<T>) {
        itemsListAdapter.set(
                list
                        .sortedBy { item -> item.ordinal }
                        .map { makeBinder(it) }
        )
        updateVisibleContent()
    }

    private fun makeBinder(item: T): B {
        return binderFactory(item, binderActionsListener)
    }
}