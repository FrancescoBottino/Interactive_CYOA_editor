package it.thefreak.android.interactivecyoaeditor.ui.home

import com.zhuinden.simplestack.ServiceBinder
import com.zhuinden.simplestackextensions.services.DefaultServiceProvider
import com.zhuinden.simplestackextensions.servicesktx.add
import it.thefreak.android.interactivecyoaeditor.FragmentKey
import kotlinx.android.parcel.Parcelize

@Parcelize
class HomeKey: FragmentKey(), DefaultServiceProvider.HasServices {
    override fun instantiateFragment() = HomeFragment()
    override fun getFragmentTag(): String = toString()
    override fun getScopeTag(): String = fragmentTag

    override fun bindServices(serviceBinder: ServiceBinder) {
        with(serviceBinder) {
            add(LocalAdventuresViewModel())
        }
    }
}