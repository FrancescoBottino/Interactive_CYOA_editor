package it.thefreak.android.interactivecyoaeditor.ui.editor.binders

import android.view.View
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.drag.IDraggable
import it.thefreak.android.interactivecyoaeditor.*
import it.thefreak.android.interactivecyoaeditor.databinding.ListItemGenericBinding
import it.thefreak.android.interactivecyoaeditor.model.IdManager
import it.thefreak.android.interactivecyoaeditor.model.Requirement
import it.thefreak.android.interactivecyoaeditor.model.RequirementType
import it.thefreak.android.interactivecyoaeditor.views.itemslisteditor.ItemsListEditorBinderListener
import it.thefreak.android.interactivecyoaeditor.views.itemslisteditor.ItemsListEditorGenericBinder

class RequirementBinder(
        requirement: Requirement,
        listener: ItemsListEditorBinderListener<RequirementBinder>? = null,
        val idManager: IdManager
) : ItemsListEditorGenericBinder<Requirement, RequirementBinder>(requirement, listener), IDraggable {

    class ViewHolder(
            private val root: View
    ): FastAdapter.ViewHolder<RequirementBinder>(root) {
        private val binding: ListItemGenericBinding = ListItemGenericBinding.bind(root)

        init {
            binding.icon.hide()
        }

        override fun bindView(item: RequirementBinder, payloads: List<Any>) {
            //TODO, represent the requirement in some descriptive way

            val typeRes = when(item.content.type) {
                RequirementType.CHOICE_SELECTION -> {R.string.requirement_type_choice_selection}
                RequirementType.POINT_COMPARISON -> {R.string.requirement_type_point_comparison}
                RequirementType.POINT_AMOUNT -> {R.string.requirement_type_point_amount}
            }
            val displayedName = root.context.getString(typeRes)

            binding.titleLabel.text = displayedName

            binding.overtext.hideIf {
                item.content.id.isNullOrBlank()
            }.text = item.content.id

            binding.subtitleLabel.hide()

            binding.deleteButton.onClick { item.listener?.onItemDelete(item) }
            binding.copyButton.onClick { item.listener?.onItemCopy(item) }
            binding.content.onClick { item.listener?.onItemClick(item) }
        }

        override fun unbindView(item: RequirementBinder) {
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
        get() = R.id.requirement_list_item

    override fun getViewHolder(v: View): ViewHolder = ViewHolder(v)
    override val isDraggable: Boolean
        get() = true
}