package it.thefreak.android.interactivecyoaeditor.ui.editor.forms.choice

import it.thefreak.android.interactivecyoaeditor.ui.editor.FragmentKey
import kotlinx.android.parcel.Parcelize

@Parcelize
class ChoiceFormKey(val choiceId: String): FragmentKey() {
    override fun instantiateFragment() = ChoiceFormFragment()
    override fun getFragmentTag(): String = toString()
}