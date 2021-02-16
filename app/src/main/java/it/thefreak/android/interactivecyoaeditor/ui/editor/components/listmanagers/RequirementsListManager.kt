package it.thefreak.android.interactivecyoaeditor.ui.editor.components.listmanagers

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import it.thefreak.android.interactivecyoaeditor.R
import it.thefreak.android.interactivecyoaeditor.model.*
import it.thefreak.android.interactivecyoaeditor.ui.editor.binders.RequirementBinder
import it.thefreak.android.interactivecyoaeditor.views.itemslisteditor.ItemsListEditorGenericManager
import it.thefreak.android.interactivecyoaeditor.views.itemslisteditor.ItemsListEditorItemListener
import it.thefreak.android.interactivecyoaeditor.views.itemslisteditor.ItemsListEditorView
import kotlin.reflect.KMutableProperty0

class RequirementsListManager(
        ctx: Context,
        itemsListEditorView: ItemsListEditorView,
        idManager: IdManager,
        container: KMutableProperty0<ArrayList<Requirement>?>,
        clickListener: (Requirement) -> Unit,
): ItemsListEditorGenericManager<Requirement, RequirementBinder>(
        ctx,
        itemsListEditorView,
        ::RequirementBinder,
        object : ItemsListEditorItemListener<Requirement> {
            override fun onItemDelete(item: Requirement): Boolean {
                item.deepDelete(idManager)
                container.init().remove(item)
                return true
            }

            override fun onItemClick(item: Requirement) {
                clickListener(item)
            }

            override fun onItemCopy(item: Requirement): Requirement {
                return when(item.type) {
                    RequirementType.CHOICE_SELECTION -> {
                        (item as ChoiceSelectionRequirement).deepCopy(idManager)
                    }
                    RequirementType.POINT_AMOUNT -> {
                        (item as PointAmountRequirement).deepCopy(idManager)
                    }
                    RequirementType.POINT_COMPARISON -> {
                        (item as PointComparisonRequirement).deepCopy(idManager)
                    }
                }.apply {
                    container.init().add(this)
                }
            }

            override fun onNewItem(adder: (Requirement) -> Unit) {
                val choiceSelection = ctx.getString(R.string.requirement_type_choice_selection)
                val pointAmount = ctx.getString(R.string.requirement_type_point_amount)
                val pointComparison = ctx.getString(R.string.requirement_type_point_comparison)

                val items = arrayOf(choiceSelection, pointAmount, pointComparison)

                MaterialAlertDialogBuilder(ctx)
                    .setTitle(ctx.getString(R.string.requirement_choice_dialog_title))
                    .setCancelable(true)
                    .setItems(items) { dialog, which ->
                        when (items[which]) {
                            choiceSelection -> ChoiceSelectionRequirement()
                            pointAmount -> PointAmountRequirement()
                            pointComparison -> PointComparisonRequirement()
                            else -> null
                        }?.let {
                            it.assignNewId(idManager)
                            container.init().add(it)
                            adder(it)
                        }
                    }.show()
            }
        }
)