package it.thefreak.android.interactivecyoaeditor.ui.editor.components

import android.content.Context
import it.thefreak.android.interactivecyoaeditor.model.PointState
import it.thefreak.android.interactivecyoaeditor.ui.editor.binders.PointStateBinder
import it.thefreak.android.interactivecyoaeditor.views.ItemsListEditor

class PointsListManager (
        ctx: Context?,
        itemsListEditor: ItemsListEditor,
        itemsListEditorListener: ItemsListEditorListener<PointState>,
): GenericItemsListManager<PointState, PointStateBinder>(
        ctx,
        itemsListEditor,
        ::PointStateBinder,
        itemsListEditorListener
)