package it.thefreak.android.interactivecyoaeditor.views.itemslisteditor

import it.thefreak.android.interactivecyoaeditor.GenericBinder

abstract class ItemsListEditorGenericBinder<T, B: GenericBinder<T>> constructor(
        content: T,
        val listener: ItemsListEditorBinderListener<B>?
) : GenericBinder<T>(content)