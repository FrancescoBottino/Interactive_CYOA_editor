package it.thefreak.android.interactivecyoaeditor

import android.os.Bundle
import com.zhuinden.simplestack.History
import com.zhuinden.simplestack.SimpleStateChanger
import com.zhuinden.simplestack.StateChange
import com.zhuinden.simplestack.navigator.Navigator
import com.zhuinden.simplestackextensions.fragments.DefaultFragmentStateChanger
import com.zhuinden.simplestackextensions.services.DefaultServiceProvider
import it.thefreak.android.interactivecyoaeditor.databinding.ActivityMainBinding
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
                //.install(this, binding.container, History.of(HomeKey()))
                /**
                 * todo use home
                 */
                .install(this, binding.container, History.of(AdventureFormKey(getFileUri(this))))
    }

    override fun onBackPressed() {
        if (!Navigator.onBackPressed(this)) {
            super.onBackPressed()
        }
    }

    override fun onNavigationEvent(stateChange: StateChange) {
        fragmentStateChanger.handleStateChange(stateChange)
    }
}