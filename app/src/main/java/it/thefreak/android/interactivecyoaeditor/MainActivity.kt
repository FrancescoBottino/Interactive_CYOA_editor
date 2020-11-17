package it.thefreak.android.interactivecyoaeditor

import android.os.Bundle
import android.view.Menu
import com.zhuinden.simplestack.History
import com.zhuinden.simplestack.SimpleStateChanger
import com.zhuinden.simplestack.StateChange
import com.zhuinden.simplestack.navigator.Navigator
import com.zhuinden.simplestackextensions.fragments.DefaultFragmentStateChanger
import com.zhuinden.simplestackextensions.services.DefaultServiceProvider
import it.thefreak.android.interactivecyoaeditor.databinding.ActivityMainBinding
import it.thefreak.android.interactivecyoaeditor.ui.editor.FragmentKey
import it.thefreak.android.interactivecyoaeditor.ui.editor.forms.adventure.AdventureFormKey


class MainActivity : BasicActivity(), SimpleStateChanger.NavigationHandler {
    private lateinit var fragmentStateChanger: DefaultFragmentStateChanger
    private var currentMenuRes: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fragmentStateChanger = DefaultFragmentStateChanger(supportFragmentManager, R.id.container)

        Navigator.configure()
                .setStateChanger(SimpleStateChanger(this))
                .setScopedServices(DefaultServiceProvider())
                .install(this, binding.container, History.of(AdventureFormKey()))
    }

    override fun onBackPressed() {
        if (!Navigator.onBackPressed(this)) {
            super.onBackPressed()
        }
    }

    override fun onNavigationEvent(stateChange: StateChange) {
        fragmentStateChanger.handleStateChange(stateChange)
        //setUpActionbar(stateChange.topNewKey<FragmentKey>())
    }

    /*

    private fun setUpActionbar(fragmentKey: FragmentKey) {
        setTitle(fragmentKey.titleRes)

        //TODO send navigation and menu click actions to fragment

        supportActionBar?.let { actionBar ->
            fragmentKey.backNavigationIcon?.let { navIcon ->
                actionBar.setDisplayHomeAsUpEnabled(true)
                actionBar.setDisplayShowHomeEnabled(true)
                actionBar.setNavigationIcon(navIcon)
            } ?: run {
                actionBar.setDisplayHomeAsUpEnabled(false)
                actionBar.setDisplayShowHomeEnabled(false)
            }
        }

        currentMenuRes = fragmentKey.menuRes
        invalidateOptionsMenu()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return currentMenuRes?.let {
            menuInflater.inflate(it, menu)
            true
        } ?: false
    }

     */
}