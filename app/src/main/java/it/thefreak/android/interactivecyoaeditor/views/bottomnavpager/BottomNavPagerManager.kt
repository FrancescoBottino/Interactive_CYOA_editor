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

    init {
        /* TODO ADD FIRST SELECTED SCREEN
        scrollToScreen(firstScreenIndex)
        selectNavViewItem(firstScreenIndex)
         */

        navView.setOnNavigationItemSelectedListener { item ->
            menuItems.indexOf(item).let {
                if(it == -1) throw Exception("Id not found")
                pager.currentItem = it
            }
            false
        }

        pager.registerOnPageChangeCallback(object: OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                navView.menu.getItem(position).isChecked = true;
            }
        })

        pager.adapter = object: FragmentStateAdapter(parent) {
            override fun createFragment(position: Int): Fragment {
                return fragments[position]
            }

            override fun getItemCount(): Int {
                return fragments.size
            }
        }
    }
}