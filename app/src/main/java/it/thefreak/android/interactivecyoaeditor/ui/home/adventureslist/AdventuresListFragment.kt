package it.thefreak.android.interactivecyoaeditor.ui.home.adventureslist

import android.os.Bundle
import android.util.Log
import android.view.View
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import com.zhuinden.simplestackextensions.fragmentsktx.backstack
import com.zhuinden.simplestackextensions.fragmentsktx.lookup
import it.thefreak.android.interactivecyoaeditor.R
import it.thefreak.android.interactivecyoaeditor.databinding.AdventuresListFragmentBinding
import it.thefreak.android.interactivecyoaeditor.model.entities.Adventure
import it.thefreak.android.interactivecyoaeditor.model.entities.AdventureMeta
import it.thefreak.android.interactivecyoaeditor.ui.editor.forms.adventure.AdventureFormKey
import it.thefreak.android.interactivecyoaeditor.ui.home.LocalAdventuresViewModel
import it.thefreak.android.interactivecyoaeditor.views.itemslisteditor.ItemsListEditorItemListener

class AdventuresListFragment: KeyedFragment(R.layout.adventures_list_fragment) {
    private val localAdventuresViewModel by lazy {
        lookup<LocalAdventuresViewModel>()
    }

    private lateinit var binding: AdventuresListFragmentBinding

    private lateinit var adventureMetaListEditor: AdventureMetaListManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = AdventuresListFragmentBinding.bind(view)
        with(binding) {
            adventureMetaListEditor = AdventureMetaListManager(
                requireContext(),
                adventuresList,
                object : ItemsListEditorItemListener<AdventureMeta> {
                    override fun onItemDelete(item: AdventureMeta): Boolean {
                        return try {
                            localAdventuresViewModel.deleteAdventure(item.uri!!)
                            true
                        } catch (e: Exception) {
                            Log.e("ADV_REPO", e.message?:"couldn't delete adventure ${item.uri}")
                            false
                        }
                    }

                    override fun onItemClick(item: AdventureMeta) {
                        backstack.goTo(AdventureFormKey(item.uri!!))
                    }

                    override fun onItemCopy(item: AdventureMeta): AdventureMeta? {
                        return try {
                            localAdventuresViewModel
                                .copyAdventure(requireContext(), item)
                        } catch (e: Exception) {
                            Log.e("ADV_REPO", e.message?:"couldn't copy adventure ${item.uri}")
                            null
                        }
                    }

                    override fun onNewItem(adder: (AdventureMeta) -> Unit) {
                        try {
                            adder(
                                localAdventuresViewModel
                                    .saveNewAdventure(requireContext(), Adventure())
                            )
                        } catch (e: Exception) {
                            Log.e("ADV_REPO", e.message?:"couldn't create new adventure")
                        }
                    }
                }
            )

            localAdventuresViewModel
                .getAdventures(requireContext())
                .observe(viewLifecycleOwner) { adventures ->
                    adventureMetaListEditor.set(adventures)
                }
        }
    }
}