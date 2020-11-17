package it.thefreak.android.interactivecyoaeditor.ui

import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.items.AbstractItem

abstract class GenericBinder<T> constructor(
        val content: T
) : AbstractItem<RecyclerView.ViewHolder>()