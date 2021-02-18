package it.thefreak.android.interactivecyoaeditor.ui.editor.components.chooserdialog

import android.content.Context
import android.view.View
import it.thefreak.android.interactivecyoaeditor.R
import it.thefreak.android.interactivecyoaeditor.databinding.ListItemGenericBinding
import it.thefreak.android.interactivecyoaeditor.hide
import it.thefreak.android.interactivecyoaeditor.hideIf
import it.thefreak.android.interactivecyoaeditor.model.IdManager
import it.thefreak.android.interactivecyoaeditor.model.entities.Choice
import it.thefreak.android.interactivecyoaeditor.views.itemselectordialog.SingleItemSelectorDialogBuilder

class ChoiceChooser(
        ctx: Context,
        idManager: IdManager,
): Chooser<Choice>(
        ctx,
        Choice::class,
        idManager,
) {
    companion object {
        private object ViewBinderObject: SingleItemSelectorDialogBuilder.ViewBinder<Choice> {
            override fun bindView(item: Choice, view: View): View {
                val binding = ListItemGenericBinding.bind(view)

                binding.titleLabel.text = if(item.name.isNullOrBlank()) {
                    view.context.getString(R.string.unnamed_choice)
                } else item.name!!

                binding.overtext.hideIf {
                    item.id.isNullOrBlank()
                }.text = item.id

                binding.icon.hide()
                binding.subtitleLabel.hide()
                binding.deleteButton.hide()
                binding.copyButton.hide()

                return binding.content
            }

            override fun unbindView(item: Choice, view: View): View {
                val binding = ListItemGenericBinding.bind(view)

                binding.titleLabel.text = ""
                binding.overtext.text = ""
                binding.icon.hide()
                binding.subtitleLabel.hide()
                binding.deleteButton.hide()
                binding.copyButton.hide()

                return binding.content
            }
        }
    }

    override val name: Int
        get() = R.string.choice_selection_requirement_dialog_title
    override val layout: Int
        get() = R.layout.list_item_generic
    override val isSearchFilteringEnabled: Boolean
        get() = true
    override val isNegativeButtonEnabled: Boolean
        get() = true
    override val viewBinder: SingleItemSelectorDialogBuilder.ViewBinder<Choice>
        get() = ViewBinderObject

    override fun List<Choice>.sorting(): List<Choice> {
        return this.sortedBy { it.name }
    }

    override fun searchFilteringPredicate(item: Choice, query: CharSequence?): Boolean {
        return item.name?.contains(query?:"", ignoreCase = true)?:false
    }
}