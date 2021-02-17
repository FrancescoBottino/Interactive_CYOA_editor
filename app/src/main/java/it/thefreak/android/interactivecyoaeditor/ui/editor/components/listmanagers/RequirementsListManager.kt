package it.thefreak.android.interactivecyoaeditor.ui.editor.components.listmanagers

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import it.thefreak.android.interactivecyoaeditor.R
import it.thefreak.android.interactivecyoaeditor.model.*
import it.thefreak.android.interactivecyoaeditor.ui.editor.binders.RequirementBinder
import it.thefreak.android.interactivecyoaeditor.ui.editor.components.listmanagers.itemslisteditorfactories.ItemsListFormPickerFactory
import it.thefreak.android.interactivecyoaeditor.views.itemslisteditor.ItemsListEditorGenericManager
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
        ItemsListFormPickerFactory(
                Requirement::class,
                idManager,
                container,
                clickListener,
                MaterialAlertDialogBuilder(ctx)
                        .setTitle(ctx.getString(R.string.requirement_choice_dialog_title)),
                mapOf(
                        RequirementType.CHOICE_SELECTION to ItemsListFormPickerFactory.Coupler(
                                ctx.getString(R.string.requirement_type_choice_selection),
                                { item, idManager ->
                                    (item as ChoiceSelectionRequirement).deepCopy(idManager)
                                },
                                ::ChoiceSelectionRequirement
                        ),
                        RequirementType.POINT_AMOUNT to ItemsListFormPickerFactory.Coupler(
                                ctx.getString(R.string.requirement_type_point_amount),
                                { item, idManager ->
                                    (item as PointAmountRequirement).deepCopy(idManager)
                                },
                                ::PointAmountRequirement
                        ),
                        RequirementType.POINT_COMPARISON to ItemsListFormPickerFactory.Coupler(
                                ctx.getString(R.string.requirement_type_point_comparison),
                                { item, idManager ->
                                    (item as PointComparisonRequirement).deepCopy(idManager)
                                },
                                ::PointComparisonRequirement
                        ),
                )
        )
)