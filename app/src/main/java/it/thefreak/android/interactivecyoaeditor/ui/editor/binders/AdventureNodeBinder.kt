package it.thefreak.android.interactivecyoaeditor.ui.editor.binders

import android.view.View
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.drag.IDraggable
import it.thefreak.android.interactivecyoaeditor.R
import it.thefreak.android.interactivecyoaeditor.databinding.ListItemGenericBinding
import it.thefreak.android.interactivecyoaeditor.hideIf
import it.thefreak.android.interactivecyoaeditor.model.AdventureNode
import it.thefreak.android.interactivecyoaeditor.onClick
import it.thefreak.android.interactivecyoaeditor.show

class AdventureNodeBinder constructor(
        adventureItem: AdventureNode,
        listener: ListItemListener<AdventureNodeBinder>? = null
) : ListItemEditorBinder<AdventureNode, AdventureNodeBinder>(adventureItem, listener), IDraggable {

    class ViewHolder(
            private val root: View
    ): FastAdapter.ViewHolder<AdventureNodeBinder>(root) {
        private val binding: ListItemGenericBinding = ListItemGenericBinding.bind(root)

        init {
            binding.icon.visibility = View.GONE
        }

        override fun bindView(item: AdventureNodeBinder, payloads: List<Any>) {
            binding.titleLabel.text = if(item.content.name.isNullOrBlank()) {
                root.context.getString(R.string.unnamed_adventure_node)
            } else item.content.name!!

            binding.overtext.hideIf {
                item.content.id.isNullOrBlank()
            }.text = item.content.id

            binding.subtitleLabel.hideIf {
                item.content.description.isNullOrBlank()
            }.text = item.content.description

            binding.deleteButton.onClick { item.listener?.onItemDelete(item) }
            binding.copyButton.onClick { item.listener?.onItemCopy(item) }
            binding.content.onClick { item.listener?.onItemClick(item) }
        }

        override fun unbindView(item: AdventureNodeBinder) {
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
        get() = R.id.adventure_node_list_item

    override fun getViewHolder(v: View): ViewHolder = ViewHolder(v)
    override val isDraggable: Boolean
        get() = true

}