package it.thefreak.android.interactivecyoaeditor.ui.editor.forms.adventure

import com.zhuinden.simplestack.ServiceBinder
import com.zhuinden.simplestackextensions.services.DefaultServiceProvider
import com.zhuinden.simplestackextensions.servicesktx.add
import it.thefreak.android.interactivecyoaeditor.ui.editor.FragmentKey
import kotlinx.android.parcel.Parcelize

@Parcelize
class AdventureFormKey: FragmentKey(), DefaultServiceProvider.HasServices {
    override fun instantiateFragment() = AdventureFormFragment()

    override fun getScopeTag(): String = fragmentTag

    override fun bindServices(serviceBinder: ServiceBinder) {
        with(serviceBinder) {
            add(AdventureFormModel(getKey(), backstack))
        }
    }
}