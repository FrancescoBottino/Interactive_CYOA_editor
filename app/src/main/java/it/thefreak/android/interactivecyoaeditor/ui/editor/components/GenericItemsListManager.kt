package it.thefreak.android.interactivecyoaeditor.ui.editor.components

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
import it.thefreak.android.interactivecyoaeditor.ui.editor.binders.ListItemEditorBinder
import it.thefreak.android.interactivecyoaeditor.ui.editor.binders.ListItemListener
import it.thefreak.android.interactivecyoaeditor.views.ItemsListEditor


abstract class GenericItemsListManager<T : ListableItem, B : ListItemEditorBinder<T, B>>(
        ctx: Context?,
        itemsListEditor: ItemsListEditor,
        val binderFactory: (T, ListItemListener<B>) -> B,
        val itemsListActionsListener: ItemsListEditorListener<T>
) {
    private var collapsed: Boolean = false

    private val itemsListAdapter: FastItemAdapter<B> = FastItemAdapter()
    private val recyclerView = itemsListEditor.binding.recyclerView
    private val collapsedViewMore = itemsListEditor.binding.collapsedViewMore
    private val emptyListPlaceholder = itemsListEditor.binding.emptyListPlaceholder

    private val addButton = itemsListEditor.binding.addButton
    private val collapseButton = itemsListEditor.binding.collapseButton

    private val itemsListTouchHelper by lazy {
        ItemTouchHelper(
                SimpleDragCallback(
                        object : ItemTouchCallback {
                            override fun itemTouchDropped(oldPosition: Int, newPosition: Int) {
                                super.itemTouchDropped(oldPosition, newPosition)
                                updatePointsPositions()
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

    private val binderActionsListener = object : ListItemListener<B> {
        override fun onItemDelete(item: B) {
            if ( itemsListActionsListener.onItemDelete(item.content) ) {
                itemsListAdapter.apply {
                    remove(getAdapterPosition(item))
                }
                updatePointsPositions()
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
                        updatePointsPositions()
                        updateVisibleContent()
                    }
                }
            }
        }
    }

    private fun updatePointsPositions() {
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

    init {
        recyclerView.layoutManager = LinearLayoutManager(ctx)
        recyclerView.itemAnimator = AlphaInAnimator()
        itemsListTouchHelper.attachToRecyclerView(recyclerView)
        recyclerView.adapter = itemsListAdapter

        updateVisibleContent()

        addButton.onClick {
            itemsListActionsListener.onNewItem()?.let { newItem ->
                newItem.ordinal = itemsListAdapter.itemCount + 1
                itemsListAdapter.add(makeBinder(newItem))

                updateVisibleContent()
            }
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