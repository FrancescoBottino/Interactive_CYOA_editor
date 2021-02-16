package it.thefreak.android.interactivecyoaeditor.ui.editor.components.listmanagers

import android.content.Context
import it.thefreak.android.interactivecyoaeditor.model.Choice
import it.thefreak.android.interactivecyoaeditor.ui.editor.binders.ChoiceRequirementBinder
import it.thefreak.android.interactivecyoaeditor.ui.editor.components.chooserdialog.ChoiceChooser
import it.thefreak.android.interactivecyoaeditor.ui.editor.components.listmanagers.itemslisteditorfactories.ItemsListPassiveIdsListenerFactory
import it.thefreak.android.interactivecyoaeditor.views.itemslisteditor.ItemsListEditorGenericManager
import it.thefreak.android.interactivecyoaeditor.views.itemslisteditor.ItemsListEditorView
import kotlin.reflect.KMutableProperty0

class ChoiceSelectionRequirementListManager(
        ctx: Context,
        itemsListEditorView: ItemsListEditorView,
        container: KMutableProperty0<HashSet<String>?>,
        chooser: ChoiceChooser
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