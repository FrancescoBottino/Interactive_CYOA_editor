package it.thefreak.android.interactivecyoaeditor.ui.editor.forms.costmodifiers.additivecostmodifier

import it.thefreak.android.interactivecyoaeditor.FragmentKey
import kotlinx.android.parcel.Parcelize

@Parcelize
class AdditiveCostModifierFormKey(val costModifierId: String): FragmentKey() {
    override fun instantiateFragment() = AdditiveCostModifierFormFragment()
    override fun getFragmentTag(): String = toString()
}