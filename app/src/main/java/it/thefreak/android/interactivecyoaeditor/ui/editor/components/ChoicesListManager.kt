package it.thefreak.android.interactivecyoaeditor.ui.editor.components

import android.content.Context
import it.thefreak.android.interactivecyoaeditor.model.Choice
import it.thefreak.android.interactivecyoaeditor.model.IdManager
import it.thefreak.android.interactivecyoaeditor.ui.editor.binders.ChoiceBinder
import it.thefreak.android.interactivecyoaeditor.views.ItemsListEditor
import kotlin.reflect.KMutableProperty0

class ChoicesListManager (
        ctx: Context?,
        itemsListEditor: ItemsListEditor,
        idManager: IdManager,
        container: KMutableProperty0<ArrayList<Choice>?>,
        clickListener: (Choice) -> Unit,
): GenericItemsListManager<Choice, ChoiceBinder>(
        ctx,
        itemsListEditor,
        ::ChoiceBinder,
        ItemsListEditorListenerFactory(
                Choice::class,
                ::Choice,
                idManager,
                container,
                clickListener
        )
)