package it.thefreak.android.interactivecyoaeditor.ui.editor.binders

import android.view.View
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.drag.IDraggable
import it.thefreak.android.interactivecyoaeditor.*
import it.thefreak.android.interactivecyoaeditor.databinding.ListItemGenericBinding
import it.thefreak.android.interactivecyoaeditor.model.Cost
import it.thefreak.android.interactivecyoaeditor.views.itemslisteditor.ItemsListEditorBinderListener
import it.thefreak.android.interactivecyoaeditor.views.itemslisteditor.ItemsListEditorGenericBinder

class CostBinder constructor(
        cost: Cost,
        listener: ItemsListEditorBinderListener<CostBinder>? = null
) : ItemsListEditorGenericBinder<Cost, CostBinder>(cost, listener), IDraggable {

    class ViewHolder(
        private val root: View
    ): FastAdapter.ViewHolder<CostBinder>(root) {
        private val binding: ListItemGenericBinding = ListItemGenericBinding.bind(root)

        init {
            binding.icon.hide()
        }

        override fun bindView(item: CostBinder, payloads: List<Any>) {
            val displayedName =
                    ifBothNotNull(
                            item.content.amount,
                            item.content.pointType,
                            default = root.context.getString(R.string.undefined_cost)
                    ) { amount, pointType ->
                        root.context.getString(
                                R.string.cost_list_title,
                                amount.toString(),
                                pointType.name ?: pointType.id!!
                        )
                    }

            binding.titleLabel.text = displayedName

            binding.overtext.hideIf {
                item.content.id.isNullOrBlank()
            }.text = item.content.id

            binding.subtitleLabel.hide()

            binding.deleteButton.onClick { item.listener?.onItemDelete(item) }
            binding.copyButton.onClick { item.listener?.onItemCopy(item) }
            binding.content.onClick { item.listener?.onItemClick(item) }
        }

        override fun unbindView(item: CostBinder) {
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
        get() = R.id.cost_list_item

    override fun getViewHolder(v: View): ViewHolder = ViewHolder(v)
    override val isDraggable: Boolean
        get() = true
}