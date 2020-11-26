package it.thefreak.android.interactivecyoaeditor.ui.editor.components

import android.content.Context
import it.thefreak.android.interactivecyoaeditor.model.Choice
import it.thefreak.android.interactivecyoaeditor.model.IdManager
import it.thefreak.android.interactivecyoaeditor.ui.editor.binders.ChoiceBinder
import it.thefreak.android.interactivecyoaeditor.views.itemslisteditor.ItemsListEditorGenericManager
import it.thefreak.android.interactivecyoaeditor.views.itemslisteditor.ItemsListEditorView
import kotlin.reflect.KMutableProperty0

class ChoicesListManager (
        ctx: Context?,
        itemsListEditorView: ItemsListEditorView,
        idManager: IdManager,
        container: KMutableProperty0<ArrayList<Choice>?>,
        clickListener: (Choice) -> Unit,
): ItemsListEditorGenericManager<Choice, ChoiceBinder>(
        ctx,
        itemsListEditorView,
        ::ChoiceBinder,
        ItemsListEditorListenerFactory(
                Choice::class,
                ::Choice,
                idManager,
                container,
                clickListener
        )
)