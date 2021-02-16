package it.thefreak.android.interactivecyoaeditor.ui.editor.components.chooserdialog

import android.content.Context
import android.view.View
import it.thefreak.android.interactivecyoaeditor.R
import it.thefreak.android.interactivecyoaeditor.databinding.ListItemGenericBinding
import it.thefreak.android.interactivecyoaeditor.hide
import it.thefreak.android.interactivecyoaeditor.hideIf
import it.thefreak.android.interactivecyoaeditor.model.Choice
import it.thefreak.android.interactivecyoaeditor.model.IdManager
import it.thefreak.android.interactivecyoaeditor.onClick
import it.thefreak.android.interactivecyoaeditor.views.itemselectordialog.SingleItemSelectorDialogBuilder

class ChoiceChooser(
        ctx: Context,
        idManager: IdManager,
): Chooser<Choice>(
        ctx,
        Choice::class,
        R.string.choice_selection_requirement_dialog_title,
        R.layout.list_item_generic,
        viewBinder,
        idManager
) {
    companion object {
        object viewBinder: SingleItemSelectorDialogBuilder.ViewBinder<Choice> {
            override fun bindView(item: Choice, view: View) {
                val binding = ListItemGenericBinding.bind(view)

                binding.titleLabel.text = if(item.name.isNullOrBlank()) {
                    view.context.getString(R.string.unnamed_choice)
                } else item.name!!

                binding.overtext.hideIf {
                    item.id.isNullOrBlank()
                }.text = item.id

                binding.subtitleLabel.hide()
                binding.deleteButton.hide()
                binding.copyButton.hide()
                binding.deleteButton.onClick(null)
                binding.copyButton.onClick(null)
                binding.content.onClick(null)
            }

            override fun unbindView(item: Choice, view: View) {
                val binding = ListItemGenericBinding.bind(view)

                binding.titleLabel.text = ""
                binding.overtext.text = ""
                binding.deleteButton.hide()
                binding.copyButton.hide()

                binding.titleLabel.text = ""
                binding.overtext.text = ""
                binding.subtitleLabel.hide()
                binding.deleteButton.onClick(null)
                binding.copyButton.onClick(null)
                binding.content.onClick(null)
            }
        }
    }
}