package it.thefreak.android.interactivecyoaeditor.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import it.thefreak.android.interactivecyoaeditor.R
import it.thefreak.android.interactivecyoaeditor.databinding.HomeFragmentBinding
import it.thefreak.android.interactivecyoaeditor.ui.home.adventureslist.AdventuresListFragment
import it.thefreak.android.interactivecyoaeditor.ui.home.playthroughlist.PlaythroughListFragment
import it.thefreak.android.interactivecyoaeditor.views.bottomnavpager.BottomNavPagerManager

class HomeFragment: KeyedFragment(R.layout.home_fragment) {
    private lateinit var binding: HomeFragmentBinding
    private lateinit var bottomNavPagerManager: BottomNavPagerManager

    private lateinit var fragments: List<Fragment>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragments = listOf(
                AdventuresListFragment(),
                PlaythroughListFragment()
        )

        binding = HomeFragmentBinding.bind(view)
        with(binding) {
            bottomNavPagerManager = BottomNavPagerManager(
                    bottomNavMenu,
                    this@HomeFragment,
                    fragments
            )
        }
    }
}