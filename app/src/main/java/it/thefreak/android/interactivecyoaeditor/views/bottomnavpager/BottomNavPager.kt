package it.thefreak.android.interactivecyoaeditor.views.bottomnavpager

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MenuInflater
import androidx.constraintlayout.widget.ConstraintLayout
import it.thefreak.android.interactivecyoaeditor.R
import it.thefreak.android.interactivecyoaeditor.databinding.BottomNavPagerBinding

class BottomNavPager @JvmOverloads constructor(
        ctx: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
): ConstraintLayout(ctx, attrs, defStyleAttr) {

    private var _binding: BottomNavPagerBinding? = null
    val binding get() = _binding!!

    init {
        _binding = BottomNavPagerBinding.inflate(LayoutInflater.from(ctx), this, true)

        val attributes = ctx.obtainStyledAttributes(
                attrs,
                R.styleable.BottomNavPager
        )

        MenuInflater(ctx).inflate(
                attributes.getResourceId(R.styleable.BottomNavPager_menu_resource, 0),
                binding.navView.menu
        )

        attributes.recycle()
    }
}