package it.thefreak.android.interactivecyoaeditor.ui.editor.forms.pointstate

import it.thefreak.android.interactivecyoaeditor.ui.editor.FragmentKey
import kotlinx.android.parcel.Parcelize

@Parcelize
class PointStateFormKey(val pointStateId: String): FragmentKey() {
    override fun instantiateFragment() = PointStateFormFragment()
    override fun getFragmentTag(): String = toString()

    /*
    @IgnoredOnParcel
    @get:StringRes
    override val titleRes: Int = R.string.point_state_form_fragment_title
    @IgnoredOnParcel
    @get:DrawableRes
    override val backNavigationIcon: Int? = R.drawable.ic_baseline_arrow_back_24
    @IgnoredOnParcel
    @get:MenuRes
    override val menuRes: Int? = null

     */
}