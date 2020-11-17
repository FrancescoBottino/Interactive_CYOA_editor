package it.thefreak.android.interactivecyoaeditor.ui.editor.components

import android.content.Context
import it.thefreak.android.interactivecyoaeditor.model.AdventureNode
import it.thefreak.android.interactivecyoaeditor.ui.editor.binders.AdventureNodeBinder
import it.thefreak.android.interactivecyoaeditor.views.ItemsListEditor

class AdventureNodesListManager (
        ctx: Context?,
        itemsListEditor: ItemsListEditor,
        itemsListEditorListener: ItemsListEditorListener<AdventureNode>,
): GenericItemsListManager<AdventureNode, AdventureNodeBinder>(
        ctx,
        itemsListEditor,
        ::AdventureNodeBinder,
        itemsListEditorListener
)