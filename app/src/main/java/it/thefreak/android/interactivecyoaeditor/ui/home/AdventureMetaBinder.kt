package it.thefreak.android.interactivecyoaeditor.ui.home

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.drag.IDraggable
import it.thefreak.android.interactivecyoaeditor.R
import it.thefreak.android.interactivecyoaeditor.databinding.ListItemGenericBinding
import it.thefreak.android.interactivecyoaeditor.hide
import it.thefreak.android.interactivecyoaeditor.model.entities.AdventureMeta
import it.thefreak.android.interactivecyoaeditor.onClick
import it.thefreak.android.interactivecyoaeditor.views.itemslisteditor.ItemsListEditorBinderListener
import it.thefreak.android.interactivecyoaeditor.views.itemslisteditor.ItemsListEditorGenericBinder

class AdventureMetaBinder(
    meta: AdventureMeta,
    listener: ItemsListEditorBinderListener<AdventureMetaBinder>?,
): ItemsListEditorGenericBinder<AdventureMeta, AdventureMetaBinder>(meta, listener), IDraggable {
    override val layoutRes: Int
        get() = R.layout.list_item_generic
    override val type: Int
        get() = R.id.adventure_list_item
    override val isDraggable: Boolean = true

    override fun getViewHolder(v: View): RecyclerView.ViewHolder = ViewHolder(v)

    class ViewHolder(root: View) : FastAdapter.ViewHolder<AdventureMetaBinder>(root) {
        private val binding: ListItemGenericBinding = ListItemGenericBinding.bind(root)

        init {
            binding.icon.hide()
            binding.subtitleLabel.hide()
        }

        override fun bindView(item: AdventureMetaBinder, payloads: List<Any>) {
            with(binding) {
                item.content.name?.let {
                    titleLabel.text = it
                } ?: run {
                    titleLabel.hide()
                }

                item.content.version?.let {
                    overtext.text = it
                } ?: run {
                    overtext.hide()
                }

                content.onClick {
                    item.listener?.onItemClick(item)
                }

                deleteButton.onClick {
                    item.listener?.onItemDelete(item)
                }

                copyButton.onClick {
                    item.listener?.onItemCopy(item)
                }
            }
        }

        override fun unbindView(item: AdventureMetaBinder) {
            with(binding) {
                titleLabel.text = null
                overtext.text = null
                content.onClick(null)
                deleteButton.onClick(null)
                copyButton.onClick(null)
            }
        }
    }
}