package it.thefreak.android.interactivecyoaeditor.ui.editor.components

import android.content.Context
import it.thefreak.android.interactivecyoaeditor.model.Choice
import it.thefreak.android.interactivecyoaeditor.ui.editor.binders.ChoiceRequirementBinder
import it.thefreak.android.interactivecyoaeditor.views.itemslisteditor.ItemsListEditorGenericManager
import it.thefreak.android.interactivecyoaeditor.views.itemslisteditor.ItemsListEditorView
import kotlin.reflect.KMutableProperty0

class ChoiceSelectionRequirementListManager(
        ctx: Context,
        itemsListEditorView: ItemsListEditorView,
        container: KMutableProperty0<HashSet<String>?>,
        chooser: Chooser<Choice>
): ItemsListEditorGenericManager<Choice, ChoiceRequirementBinder>(
        ctx,
        itemsListEditorView,
        ::ChoiceRequirementBinder,
        ItemsListPassiveIdsListenerFactory(
                Choice::class,
                chooser,
                container,
        )
)