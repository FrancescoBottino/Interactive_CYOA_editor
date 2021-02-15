package it.thefreak.android.interactivecyoaeditor.ui.editor.forms.requirements.choiceselection

import it.thefreak.android.interactivecyoaeditor.FragmentKey
import kotlinx.android.parcel.Parcelize

@Parcelize
class ChoiceSelectionRequirementFormKey(val choiceSelectionRequirementId: String): FragmentKey() {
    override fun instantiateFragment() = ChoiceSelectionRequirementFormFragment()
    override fun getFragmentTag(): String = toString()
}