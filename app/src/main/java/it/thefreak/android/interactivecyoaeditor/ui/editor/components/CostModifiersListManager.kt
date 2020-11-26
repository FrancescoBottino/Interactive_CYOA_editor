package it.thefreak.android.interactivecyoaeditor.ui.editor.components

import android.content.Context
import it.thefreak.android.interactivecyoaeditor.model.CostModifier
import it.thefreak.android.interactivecyoaeditor.model.IdManager
import it.thefreak.android.interactivecyoaeditor.ui.editor.binders.CostModifierBinder
import it.thefreak.android.interactivecyoaeditor.views.itemslisteditor.ItemsListEditorGenericManager
import it.thefreak.android.interactivecyoaeditor.views.itemslisteditor.ItemsListEditorView
import kotlin.reflect.KMutableProperty0

class CostModifiersListManager (
        ctx: Context?,
        itemsListEditorView: ItemsListEditorView,
        idManager: IdManager,
        container: KMutableProperty0<ArrayList<CostModifier>?>,
        clickListener: (CostModifier) -> Unit,
): ItemsListEditorGenericManager<CostModifier, CostModifierBinder>(
        ctx,
        itemsListEditorView,
        ::CostModifierBinder,
        ItemsListEditorListenerFactory(
                CostModifier::class,
                ::CostModifier,
                idManager,
                container,
                clickListener
        )
)