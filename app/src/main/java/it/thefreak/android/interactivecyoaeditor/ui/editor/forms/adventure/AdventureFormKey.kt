package it.thefreak.android.interactivecyoaeditor.ui.editor.forms.adventure

import android.net.Uri
import com.zhuinden.simplestack.ServiceBinder
import com.zhuinden.simplestackextensions.services.DefaultServiceProvider
import com.zhuinden.simplestackextensions.servicesktx.add
import it.thefreak.android.interactivecyoaeditor.FragmentKey
import kotlinx.android.parcel.Parcelize

@Parcelize
class AdventureFormKey(
        private val advUri: Uri
): FragmentKey(), DefaultServiceProvider.HasServices {
    override fun instantiateFragment() = AdventureFormFragment()
    override fun getFragmentTag(): String = toString()
    override fun getScopeTag(): String = fragmentTag

    override fun bindServices(serviceBinder: ServiceBinder) {
        with(serviceBinder) {
            add(AdventureFormModelLoaderRepository(advUri))
            add(AdventureFormModelIdManagerLoader())
        }
    }
}