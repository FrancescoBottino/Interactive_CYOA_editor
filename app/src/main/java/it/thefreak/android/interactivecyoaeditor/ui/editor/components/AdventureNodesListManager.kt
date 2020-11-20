package it.thefreak.android.interactivecyoaeditor.ui.editor.components

import android.content.Context
import it.thefreak.android.interactivecyoaeditor.model.AdventureNode
import it.thefreak.android.interactivecyoaeditor.model.IdManager
import it.thefreak.android.interactivecyoaeditor.ui.editor.binders.AdventureNodeBinder
import it.thefreak.android.interactivecyoaeditor.views.ItemsListEditor
import kotlin.reflect.KMutableProperty0

class AdventureNodesListManager (
        ctx: Context?,
        itemsListEditor: ItemsListEditor,
        idManager: IdManager,
        container: KMutableProperty0<ArrayList<AdventureNode>?>,
        clickListener: (AdventureNode) -> Unit,
): GenericItemsListManager<AdventureNode, AdventureNodeBinder>(
        ctx,
        itemsListEditor,
        ::AdventureNodeBinder,
        ItemsListEditorListenerFactory(
                AdventureNode::class,
                ::AdventureNode,
                idManager,
                container,
                clickListener
        )
)