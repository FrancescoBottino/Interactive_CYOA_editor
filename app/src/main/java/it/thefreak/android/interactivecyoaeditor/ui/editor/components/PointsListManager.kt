package it.thefreak.android.interactivecyoaeditor.ui.editor.components

import android.content.Context
import it.thefreak.android.interactivecyoaeditor.model.PointType
import it.thefreak.android.interactivecyoaeditor.ui.editor.binders.PointTypeBinder
import it.thefreak.android.interactivecyoaeditor.views.ItemsListEditor

class PointsListManager (
        ctx: Context?,
        itemsListEditor: ItemsListEditor,
        itemsListEditorListener: ItemsListEditorListener<PointType>,
): GenericItemsListManager<PointType, PointTypeBinder>(
        ctx,
        itemsListEditor,
        ::PointTypeBinder,
        itemsListEditorListener
)