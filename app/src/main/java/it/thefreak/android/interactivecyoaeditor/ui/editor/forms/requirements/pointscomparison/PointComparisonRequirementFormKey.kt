package it.thefreak.android.interactivecyoaeditor.ui.editor.forms.requirements.pointscomparison

import it.thefreak.android.interactivecyoaeditor.FragmentKey
import kotlinx.android.parcel.Parcelize

@Parcelize
class PointComparisonRequirementFormKey(val pointComparisonRequirementId: String): FragmentKey() {
    override fun instantiateFragment() = PointComparisonRequirementFormFragment()
    override fun getFragmentTag(): String = toString()
}