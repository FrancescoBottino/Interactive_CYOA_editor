package it.thefreak.android.interactivecyoaeditor.views.bottomnavpager

import android.view.MenuItem
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.bottomnavigation.BottomNavigationView

class BottomNavPagerManager(
        bottomNavPager: BottomNavPager,
        parent: Fragment,
        private val fragments: List<Fragment>,
        firstScreenIndex: Int = 0,
) {
    private val pager: ViewPager2 = bottomNavPager.binding.pager
    private val navView: BottomNavigationView = bottomNavPager.binding.navView

    private val menuItems: List<MenuItem> = navView.menu.children.toList()
    private val firstScreenFragment: Fragment = fragments[firstScreenIndex]
    private val firstScreenMenuItem: MenuItem = menuItems[firstScreenIndex]

    private val navViewAction = BottomNavPagerItemSelectedListener(::scrollToScreen)
    private val pagerAdapter = BottomNavPagerAdapter(parent, fragments)
    private val pageChangeCallback = BottomNavPagerChangeCallback(::selectBottomNavigationViewMenuItem)

    init {
        scrollToScreen(firstScreenFragment)
        selectBottomNavigationViewMenuItem(firstScreenMenuItem)

        pager.adapter = pagerAdapter
        pager.registerOnPageChangeCallback(pageChangeCallback)
    }


    private fun scrollToScreen(menuItem: MenuItem) {
        scrollToScreen(menuItems.indexOf(menuItem))
    }

    private fun scrollToScreen(screenFragment: Fragment) {
        scrollToScreen(fragments.indexOf(screenFragment))
    }

    private fun scrollToScreen(screenIndex: Int) {
        if (screenIndex != pager.currentItem) {
            pager.currentItem = screenIndex
        }
    }

    private fun selectBottomNavigationViewMenuItem(screenIndex: Int) {
        selectBottomNavigationViewMenuItem(menuItems[screenIndex])
    }

    private fun selectBottomNavigationViewMenuItem(menuItem: MenuItem) {
        navView.setOnNavigationItemSelectedListener(null)
        navView.selectedItemId = menuItem.itemId
        navView.setOnNavigationItemSelectedListener(navViewAction)
    }

    private class BottomNavPagerItemSelectedListener(
        val selectionListener: (MenuItem)->Unit
    ): BottomNavigationView.OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            selectionListener(item)
            return true
        }
    }

    private class BottomNavPagerAdapter(
        parent: Fragment,
        val fragments: List<Fragment>
    ): FragmentStateAdapter(parent) {
        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }

        override fun getItemCount(): Int {
            return fragments.size
        }
    }

    private class BottomNavPagerChangeCallback(
        val navHandler: (Int)->Unit
    ): OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            navHandler(position)
        }
    }
}