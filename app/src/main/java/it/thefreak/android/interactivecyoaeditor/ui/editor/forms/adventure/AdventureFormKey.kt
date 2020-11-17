package it.thefreak.android.interactivecyoaeditor.ui.editor.forms.adventure

import androidx.annotation.DrawableRes
import androidx.annotation.MenuRes
import androidx.annotation.StringRes
import com.zhuinden.simplestack.ServiceBinder
import com.zhuinden.simplestackextensions.fragments.DefaultFragmentKey
import com.zhuinden.simplestackextensions.services.DefaultServiceProvider
import com.zhuinden.simplestackextensions.servicesktx.add
import it.thefreak.android.interactivecyoaeditor.ui.editor.FragmentKey
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import it.thefreak.android.interactivecyoaeditor.R

@Parcelize
class AdventureFormKey: FragmentKey(), DefaultServiceProvider.HasServices {
    override fun instantiateFragment() = AdventureFormFragment()

    override fun getScopeTag(): String = fragmentTag

    override fun bindServices(serviceBinder: ServiceBinder) {
        with(serviceBinder) {
            add(AdventureFormModel(getKey(), backstack))
        }
    }

    /*
    @IgnoredOnParcel
    @get:StringRes
    override val titleRes: Int = R.string.adventure_form_fragment_title

    @IgnoredOnParcel
    @get:DrawableRes
    override val backNavigationIcon: Int? = null

    @IgnoredOnParcel
    @get:MenuRes
    override val menuRes: Int? = R.menu.editor_menu

     */
}