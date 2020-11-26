package it.thefreak.android.interactivecyoaeditor.views.itemslisteditor

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import it.thefreak.android.interactivecyoaeditor.R
import it.thefreak.android.interactivecyoaeditor.databinding.ItemsListEditorBinding

class ItemsListEditorView @JvmOverloads constructor(
        ctx: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
): ConstraintLayout(ctx, attrs, defStyleAttr) {

    private var _binding: ItemsListEditorBinding? = null
    val binding get() = _binding!!

    init {
        _binding = ItemsListEditorBinding.inflate(LayoutInflater.from(ctx), this, true)

        val attributes = ctx.obtainStyledAttributes(
                attrs,
                R.styleable.ItemsListEditor
        )

        binding.title.text = attributes.getString(R.styleable.ItemsListEditor_title_text)?:ctx.getString(R.string.items_list_editor_title)

        val windowBackgroundColor = TypedValue()
        if (ctx.theme.resolveAttribute(android.R.attr.windowBackground, windowBackgroundColor, true)) {
            val colorWindowBackground = windowBackgroundColor.data
            binding.addButton.background.setTint(colorWindowBackground)
            binding.title.background.setTint(colorWindowBackground)
        }

        attributes.recycle()
    }
}