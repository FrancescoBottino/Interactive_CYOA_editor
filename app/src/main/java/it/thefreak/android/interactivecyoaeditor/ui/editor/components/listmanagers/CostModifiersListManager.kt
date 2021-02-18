package it.thefreak.android.interactivecyoaeditor.ui.editor.components.listmanagers

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import it.thefreak.android.interactivecyoaeditor.R
import it.thefreak.android.interactivecyoaeditor.model.IdManager
import it.thefreak.android.interactivecyoaeditor.model.entities.AdditiveCostModifier
import it.thefreak.android.interactivecyoaeditor.model.entities.CostModifier
import it.thefreak.android.interactivecyoaeditor.model.entities.CostModifierType
import it.thefreak.android.interactivecyoaeditor.model.entities.MultiplicativeCostModifier
import it.thefreak.android.interactivecyoaeditor.ui.editor.binders.CostModifierBinder
import it.thefreak.android.interactivecyoaeditor.ui.editor.components.listmanagers.itemslisteditorfactories.ItemsListFormPickerFactory
import it.thefreak.android.interactivecyoaeditor.views.itemslisteditor.ItemsListEditorGenericManager
import it.thefreak.android.interactivecyoaeditor.views.itemslisteditor.ItemsListEditorView
import kotlin.reflect.KMutableProperty0

class CostModifiersListManager (
    ctx: Context,
    itemsListEditorView: ItemsListEditorView,
    idManager: IdManager,
    container: KMutableProperty0<ArrayList<CostModifier>?>,
    clickListener: (CostModifier) -> Unit,
): ItemsListEditorGenericManager<CostModifier, CostModifierBinder>(
        ctx,
        itemsListEditorView,
        ::CostModifierBinder,
        ItemsListFormPickerFactory(
                CostModifier::class,
                idManager,
                container,
                clickListener,
                MaterialAlertDialogBuilder(ctx)
                        .setTitle(ctx.getString(R.string.requirement_choice_dialog_title)),
                mapOf(
                        CostModifierType.ADDITIVE to ItemsListFormPickerFactory.Coupler(
                                ctx.getString(R.string.cost_modifier_type_additive),
                                { item, idManager ->
                                        (item as AdditiveCostModifier).deepCopy(idManager)
                                },
                                ::AdditiveCostModifier
                        ),
                        CostModifierType.MULTIPLICATIVE to ItemsListFormPickerFactory.Coupler(
                                ctx.getString(R.string.cost_modifier_type_multiplicative),
                                { item, idManager ->
                                        (item as MultiplicativeCostModifier).deepCopy(idManager)
                                },
                                ::MultiplicativeCostModifier
                        )
                )
        )
)