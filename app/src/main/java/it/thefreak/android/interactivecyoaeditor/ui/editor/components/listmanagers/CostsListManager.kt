package it.thefreak.android.interactivecyoaeditor.ui.editor.components.listmanagers

import android.content.Context
import it.thefreak.android.interactivecyoaeditor.model.IdManager
import it.thefreak.android.interactivecyoaeditor.model.entities.Cost
import it.thefreak.android.interactivecyoaeditor.ui.editor.binders.CostBinder
import it.thefreak.android.interactivecyoaeditor.ui.editor.components.listmanagers.itemslisteditorfactories.ItemsListEditorListenerFactory
import it.thefreak.android.interactivecyoaeditor.views.itemslisteditor.ItemsListEditorGenericManager
import it.thefreak.android.interactivecyoaeditor.views.itemslisteditor.ItemsListEditorView
import kotlin.reflect.KMutableProperty0

class CostsListManager (
    ctx: Context,
    itemsListEditorView: ItemsListEditorView,
    idManager: IdManager,
    container: KMutableProperty0<ArrayList<Cost>?>,
    clickListener: (Cost) -> Unit,
): ItemsListEditorGenericManager<Cost, CostBinder>(
        ctx,
        itemsListEditorView,
        { cost, listener ->
                CostBinder(cost, listener, idManager)
        },
        ItemsListEditorListenerFactory(
                Cost::class,
                ::Cost,
                idManager,
                container,
                clickListener
        )
)