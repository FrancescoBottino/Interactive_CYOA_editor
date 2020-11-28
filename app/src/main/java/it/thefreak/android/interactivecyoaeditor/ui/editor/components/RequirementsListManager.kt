package it.thefreak.android.interactivecyoaeditor.ui.editor.components

import android.content.Context
import it.thefreak.android.interactivecyoaeditor.model.IdManager
import it.thefreak.android.interactivecyoaeditor.model.Requirement
import it.thefreak.android.interactivecyoaeditor.ui.editor.binders.RequirementBinder
import it.thefreak.android.interactivecyoaeditor.views.itemslisteditor.ItemsListEditorGenericManager
import it.thefreak.android.interactivecyoaeditor.views.itemslisteditor.ItemsListEditorView
import kotlin.reflect.KMutableProperty0

class RequirementsListManager(
        ctx: Context?,
        itemsListEditorView: ItemsListEditorView,
        idManager: IdManager,
        container: KMutableProperty0<ArrayList<Requirement>?>,
        clickListener: (Requirement) -> Unit,
        factory: ()->Requirement?,
): ItemsListEditorGenericManager<Requirement, RequirementBinder>(
        ctx,
        itemsListEditorView,
        ::RequirementBinder,
        ItemsListEditorListenerFactory(
                Requirement::class,
                factory,
                idManager,
                container,
                clickListener
        )
)