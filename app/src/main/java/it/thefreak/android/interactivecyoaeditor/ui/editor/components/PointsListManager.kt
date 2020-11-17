package it.thefreak.android.interactivecyoaeditor.ui.editor.components

import android.content.Context
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.adapters.FastItemAdapter
import com.mikepenz.fastadapter.drag.ItemTouchCallback
import com.mikepenz.fastadapter.drag.SimpleDragCallback
import com.mikepenz.fastadapter.utils.DragDropUtil
import com.mikepenz.itemanimators.AlphaInAnimator
import it.thefreak.android.interactivecyoaeditor.model.PointState
import it.thefreak.android.interactivecyoaeditor.onClick
import it.thefreak.android.interactivecyoaeditor.ui.editor.binders.ListItemListener
import it.thefreak.android.interactivecyoaeditor.ui.editor.binders.PointStateBinder
import it.thefreak.android.interactivecyoaeditor.views.ItemsListEditor

class PointsListManager (
    ctx: Context?,
    itemsListEditor: ItemsListEditor,
    private val listener: ItemsListEditorListener<PointState>
) {
    private val pointsListAdapter: FastItemAdapter<PointStateBinder> = FastItemAdapter()
    private val recyclerView = itemsListEditor.binding.recyclerView
    private val addButton = itemsListEditor.binding.addButton

    private val pointsListTouchHelper by lazy {
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
                        DragDropUtil.onMove(pointsListAdapter.itemAdapter, oldPosition, newPosition) // change position
                        return true
                    }
                }
            )
        )
    }

    private val pointsListener = object : ListItemListener<PointStateBinder> {
        override fun onItemDelete(item: PointStateBinder) {
            pointsListAdapter.apply {
                remove(getAdapterPosition(item))
            }
            updatePointsPositions()
            listener.onItemDelete(item.pointState)
        }

        override fun onItemClick(item: PointStateBinder) {
            listener.onItemClick(item.pointState)
        }

        override fun onItemCopy(item: PointStateBinder) {
            makeBinder(listener.onItemCopy(item.pointState)).let { copiedItem ->
                pointsListAdapter.apply {
                    add(getAdapterPosition(item)+1, copiedItem)
                    updatePointsPositions()
                }
            }
        }
    }

    private fun updatePointsPositions() {
        pointsListAdapter.adapterItems.forEach {
            it.pointState.ordinal = pointsListAdapter.adapterItems.indexOf(it) + 1
        }
    }

    init {
        recyclerView.layoutManager = LinearLayoutManager(ctx)
        recyclerView.itemAnimator = AlphaInAnimator()
        pointsListTouchHelper.attachToRecyclerView(recyclerView)
        recyclerView.adapter = pointsListAdapter

        addButton.onClick {
            listener.onNewItem().let { newPoints ->
                newPoints.ordinal = pointsListAdapter.itemCount + 1
                pointsListAdapter.add(makeBinder(newPoints))
            }
        }
    }

    private fun makeBinder(item: PointState): PointStateBinder {
        return PointStateBinder(item, pointsListener)
    }

    fun set(list: List<PointState>) {
        pointsListAdapter.set(
            list
                .sortedBy { pointState -> pointState.ordinal }
                .map{ makeBinder(it) }
        )
    }
}