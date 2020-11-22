package it.thefreak.android.interactivecyoaeditor.ui.editor.forms.cost

import it.thefreak.android.interactivecyoaeditor.ui.editor.FragmentKey
import kotlinx.android.parcel.Parcelize

@Parcelize
class CostFormKey(val costId: String): FragmentKey() {
    override fun instantiateFragment() = CostFormFragment()
    override fun getFragmentTag(): String = toString()
}