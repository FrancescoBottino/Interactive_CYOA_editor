package it.thefreak.android.interactivecyoaeditor.ui.editor.forms.costmodifier

import it.thefreak.android.interactivecyoaeditor.FragmentKey
import kotlinx.android.parcel.Parcelize

@Parcelize
class CostModifierFormKey(val costModifierId: String): FragmentKey() {
    override fun instantiateFragment() = CostModifierFormFragment()
    override fun getFragmentTag(): String = toString()
}