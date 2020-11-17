package it.thefreak.android.interactivecyoaeditor.ui.editor.binders

import android.view.View
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.drag.IDraggable
import com.mikepenz.fastadapter.items.AbstractItem
import it.thefreak.android.interactivecyoaeditor.*
import it.thefreak.android.interactivecyoaeditor.databinding.ListItemGenericBinding
import it.thefreak.android.interactivecyoaeditor.model.PointState

class PointStateBinder constructor(
    val pointState: PointState,
    val listener: ListItemListener<PointStateBinder>? = null
) : AbstractItem<PointStateBinder.ViewHolder>(), IDraggable {

    class ViewHolder(
        private val root: View
    ): FastAdapter.ViewHolder<PointStateBinder>(root) {
        private val binding: ListItemGenericBinding = ListItemGenericBinding.bind(root)

        init {
            binding.icon.visibility = View.GONE
        }

        override fun bindView(item: PointStateBinder, payloads: List<Any>) {
            binding.titleLabel.text = if(item.pointState.name.isNullOrBlank()) {
                root.context.getString(R.string.unnamed_points)
            } else item.pointState.name!!

            binding.overtext.hideIf {
                item.pointState.id.isNullOrBlank()
            }.text = item.pointState.id

            binding.subtitleLabel.hideIf {
                item.pointState.description.isNullOrBlank()
            }.text = item.pointState.description

            binding.deleteButton.onClick { item.listener?.onItemDelete(item) }
            binding.copyButton.onClick { item.listener?.onItemCopy(item) }
            binding.content.onClick { item.listener?.onItemClick(item) }
        }

        override fun unbindView(item: PointStateBinder) {
            binding.titleLabel.text = ""
            binding.overtext.show()
            binding.overtext.text = ""
            binding.subtitleLabel.show()
            binding.subtitleLabel.text = ""
            binding.deleteButton.onClick(null)
            binding.copyButton.onClick(null)
            binding.content.onClick(null)
        }
    }

    override val layoutRes: Int
        get() = R.layout.list_item_generic
    override val type: Int
        get() = R.id.point_state_list_item

    override fun getViewHolder(v: View): ViewHolder = ViewHolder(v)
    override val isDraggable: Boolean = true
}