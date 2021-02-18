package it.thefreak.android.interactivecyoaeditor

import com.zhuinden.simplestack.History
import it.thefreak.android.interactivecyoaeditor.ui.editor.forms.adventure.AdventureFormKey

class MainActivity: NavigationHostActivity() {
    override fun getInitialKeys(): List<*> {
        return History.of(AdventureFormKey(getFileUri(this)))
    }
}