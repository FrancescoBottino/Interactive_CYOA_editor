package it.thefreak.android.interactivecyoaeditor.ui.editor.forms.requirements.pointsamount

import it.thefreak.android.interactivecyoaeditor.FragmentKey
import kotlinx.android.parcel.Parcelize

@Parcelize
class PointAmountRequirementFormKey(val pointAmountRequirementId: String): FragmentKey() {
    override fun instantiateFragment() = PointAmountRequirementFormFragment()
    override fun getFragmentTag(): String = toString()
}