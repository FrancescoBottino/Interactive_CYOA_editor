package it.thefreak.android.interactivecyoaeditor

import com.zhuinden.simplestack.History
import it.thefreak.android.interactivecyoaeditor.ui.home.HomeKey

class MainActivity: NavigationHostActivity() {
    override fun getInitialKeys(): List<*> {
        return History.of(HomeKey())
    }
}