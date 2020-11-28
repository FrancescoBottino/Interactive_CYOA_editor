package it.thefreak.android.interactivecyoaeditor.ui.editor.components

import android.content.Context
import it.thefreak.android.interactivecyoaeditor.model.AdventureNode
import it.thefreak.android.interactivecyoaeditor.model.IdManager
import it.thefreak.android.interactivecyoaeditor.ui.editor.binders.AdventureNodeBinder
import it.thefreak.android.interactivecyoaeditor.views.itemslisteditor.ItemsListEditorGenericManager
import it.thefreak.android.interactivecyoaeditor.views.itemslisteditor.ItemsListEditorView
import kotlin.reflect.KMutableProperty0

class AdventureNodesListManager (
        ctx: Context,
        itemsListEditorView: ItemsListEditorView,
        idManager: IdManager,
        container: KMutableProperty0<ArrayList<AdventureNode>?>,
        clickListener: (AdventureNode) -> Unit,
): ItemsListEditorGenericManager<AdventureNode, AdventureNodeBinder>(
        ctx,
        itemsListEditorView,
        ::AdventureNodeBinder,
        ItemsListEditorListenerFactory(
                AdventureNode::class,
                ::AdventureNode,
                idManager,
                container,
                clickListener
        )
)