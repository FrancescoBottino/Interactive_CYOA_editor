package it.thefreak.android.interactivecyoaeditor.ui.home.adventureslist

import android.content.Context
import it.thefreak.android.interactivecyoaeditor.model.entities.AdventureMeta
import it.thefreak.android.interactivecyoaeditor.views.itemslisteditor.ItemsListEditorGenericManager
import it.thefreak.android.interactivecyoaeditor.views.itemslisteditor.ItemsListEditorItemListener
import it.thefreak.android.interactivecyoaeditor.views.itemslisteditor.ItemsListEditorView

class AdventureMetaListManager (
    ctx: Context,
    itemsListEditorView: ItemsListEditorView,
    itemsListActionsListener: ItemsListEditorItemListener<AdventureMeta>,
): ItemsListEditorGenericManager<AdventureMeta, AdventureMetaBinder>(
        ctx,
        itemsListEditorView,
        ::AdventureMetaBinder,
        itemsListActionsListener,
)