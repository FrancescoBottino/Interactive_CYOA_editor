package it.thefreak.android.interactivecyoaeditor.ui.editor.components

import android.content.Context
import it.thefreak.android.interactivecyoaeditor.model.IdManager
import it.thefreak.android.interactivecyoaeditor.model.PointType
import it.thefreak.android.interactivecyoaeditor.ui.editor.binders.PointTypeBinder
import it.thefreak.android.interactivecyoaeditor.views.ItemsListEditor
import kotlin.reflect.KMutableProperty0

class PointsListManager (
        ctx: Context?,
        itemsListEditor: ItemsListEditor,
        idManager: IdManager,
        container: KMutableProperty0<ArrayList<PointType>?>,
        clickListener: (PointType) -> Unit,
): GenericItemsListManager<PointType, PointTypeBinder>(
        ctx,
        itemsListEditor,
        ::PointTypeBinder,
        ItemsListEditorListenerFactory(
                PointType::class,
                ::PointType,
                idManager,
                container,
                clickListener
        )
)