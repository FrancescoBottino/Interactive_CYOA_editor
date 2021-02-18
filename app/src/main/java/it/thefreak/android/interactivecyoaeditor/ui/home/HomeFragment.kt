package it.thefreak.android.interactivecyoaeditor.ui.home

import android.os.Bundle
import android.view.View
import androidx.core.net.toFile
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import com.zhuinden.simplestackextensions.fragmentsktx.lookup
import it.thefreak.android.interactivecyoaeditor.R
import it.thefreak.android.interactivecyoaeditor.databinding.HomeFragmentBinding
import it.thefreak.android.interactivecyoaeditor.model.entities.Adventure
import it.thefreak.android.interactivecyoaeditor.model.entities.AdventureMeta
import it.thefreak.android.interactivecyoaeditor.utils.JsonFileHandler.loadFromJsonFile
import it.thefreak.android.interactivecyoaeditor.views.itemslisteditor.ItemsListEditorItemListener

class HomeFragment: KeyedFragment(R.layout.home_fragment) {
    private val homeModel by lazy { lookup<HomeModel>() }

    private lateinit var binding: HomeFragmentBinding

    private lateinit var adventuresMetaListEditor: AdventuresMetaListManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = HomeFragmentBinding.bind(view)
        with(binding) {
            adventuresMetaListEditor = AdventuresMetaListManager(
                requireContext(),
                adventuresList,
                object : ItemsListEditorItemListener<AdventureMeta> {
                    override fun onItemDelete(item: AdventureMeta): Boolean {
                        return false
                    }

                    override fun onItemClick(item: AdventureMeta) {
                        //Todo fix
                        //backstack.goTo(AdventureFormKey(item.adventureUri!!))
                    }

                    override fun onItemCopy(item: AdventureMeta): AdventureMeta? {
                        TODO("Not yet implemented")
                    }

                    override fun onNewItem(adder: (AdventureMeta) -> Unit) {
                        val newAdv = Adventure().apply {
                            name = "test adv"
                            version = "1.0.0"
                        }
                        adder(homeModel.repo.newAdventure(requireContext(), newAdv))
                    }
                }
            )

            homeModel.repo
                .getAdventuresList(requireContext())
                .observe(viewLifecycleOwner) { uriPairList ->
                    uriPairList.mapNotNull {
                        loadFromJsonFile<AdventureMeta>(it.second.toFile())
                    }.let { list ->
                        adventuresMetaListEditor.set(list)
                    }
                }
        }
    }
}