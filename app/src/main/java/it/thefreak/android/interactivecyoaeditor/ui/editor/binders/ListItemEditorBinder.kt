package it.thefreak.android.interactivecyoaeditor.ui.editor.binders

import it.thefreak.android.interactivecyoaeditor.ui.GenericBinder

abstract class ListItemEditorBinder<T, B: GenericBinder<T>> constructor(
        content: T,
        val listener: ListItemListener<B>?
) : GenericBinder<T>(content)