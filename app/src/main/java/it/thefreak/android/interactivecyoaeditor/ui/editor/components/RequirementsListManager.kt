package it.thefreak.android.interactivecyoaeditor.ui.editor.components

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
                when(item.type) {
                    RequirementType.CHOICE_SELECTION -> {
                        (item as ChoiceSelectionRequirement).deepDeleteItem(idManager, container.get() as ArrayList<ChoiceSelectionRequirement>?)
                    }
                    RequirementType.POINT_AMOUNT -> {
                        (item as PointAmountRequirement).deepDeleteItem(idManager, container.get() as ArrayList<PointAmountRequirement>?)
                    }
                    RequirementType.POINT_COMPARISON -> {
                        (item as PointComparisonRequirement).deepDeleteItem(idManager, container.get() as ArrayList<PointComparisonRequirement>?)
                    }
                }
                return true
            }

            override fun onItemClick(item: Requirement) {
                clickListener(item)
            }

            override fun onItemCopy(item: Requirement): Requirement {
                return when(item.type) {
                    RequirementType.CHOICE_SELECTION -> {
                        (item as ChoiceSelectionRequirement).deepCopyItem(idManager)
                    }
                    RequirementType.POINT_AMOUNT -> {
                        (item as PointAmountRequirement).deepCopyItem(idManager)
                    }
                    RequirementType.POINT_COMPARISON -> {
                        (item as PointComparisonRequirement).deepCopyItem(idManager)
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