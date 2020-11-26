package it.thefreak.android.interactivecyoaeditor.ui.editor.forms.pointstate

import it.thefreak.android.interactivecyoaeditor.FragmentKey
import kotlinx.android.parcel.Parcelize

@Parcelize
class PointTypeFormKey(val pointTypeId: String): FragmentKey() {
    override fun instantiateFragment() = PointTypeFormFragment()
    override fun getFragmentTag(): String = toString()
}