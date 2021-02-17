package it.thefreak.android.interactivecyoaeditor.ui.editor.forms.costmodifiers.multiplicativecostmodifier

import it.thefreak.android.interactivecyoaeditor.FragmentKey
import kotlinx.android.parcel.Parcelize

@Parcelize
class MultiplicativeCostModifierFormKey(val costModifierId: String): FragmentKey() {
    override fun instantiateFragment() = MultiplicativeCostModifierFormFragment()
    override fun getFragmentTag(): String = toString()
}