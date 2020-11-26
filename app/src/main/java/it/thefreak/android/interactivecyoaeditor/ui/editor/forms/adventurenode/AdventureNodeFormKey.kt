package it.thefreak.android.interactivecyoaeditor.ui.editor.forms.adventurenode

import it.thefreak.android.interactivecyoaeditor.FragmentKey
import kotlinx.android.parcel.Parcelize

@Parcelize
class AdventureNodeFormKey(val adventureNodeId: String): FragmentKey() {
    override fun instantiateFragment() = AdventureNodeFormFragment()
    override fun getFragmentTag(): String = toString()
}