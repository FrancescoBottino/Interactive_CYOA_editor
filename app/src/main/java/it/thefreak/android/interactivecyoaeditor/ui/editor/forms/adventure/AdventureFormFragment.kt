package it.thefreak.android.interactivecyoaeditor.ui.editor.forms.adventure

import android.os.Bundle
import android.view.View
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import com.zhuinden.simplestackextensions.fragmentsktx.backstack
import com.zhuinden.simplestackextensions.fragmentsktx.lookup
import it.thefreak.android.interactivecyoaeditor.R
import it.thefreak.android.interactivecyoaeditor.databinding.AdventureFormFragmentBinding
import it.thefreak.android.interactivecyoaeditor.model.entities.Adventure
import it.thefreak.android.interactivecyoaeditor.onTextChanged
import it.thefreak.android.interactivecyoaeditor.ui.editor.components.listmanagers.AdventureNodesListManager
import it.thefreak.android.interactivecyoaeditor.ui.editor.components.listmanagers.PointsListManager
import it.thefreak.android.interactivecyoaeditor.ui.editor.forms.adventurenode.AdventureNodeFormKey
import it.thefreak.android.interactivecyoaeditor.ui.editor.forms.pointstate.PointTypeFormKey

class AdventureFormFragment: KeyedFragment(R.layout.adventure_form_fragment) {
    private val advRepoModel by lazy { lookup<AdventureFormModelLoaderRepository>() }
    private val idMaganerModel by lazy { lookup<AdventureFormModelIdManagerLoader>() }

    private lateinit var binding: AdventureFormFragmentBinding
    private lateinit var pointsListManager: PointsListManager
    private lateinit var adventureNodesListManager: AdventureNodesListManager

    private lateinit var adventure: Adventure

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adventure = advRepoModel.getAdventure(idMaganerModel.idManager)

        binding = AdventureFormFragmentBinding.bind(view)
        with(binding) {
            topAppBar.setNavigationOnClickListener {
                backstack.goBack()
            }

            topAppBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.editor_menu_save_action -> {
                        //TODO handle save action
                        advRepoModel.saveAdventure(adventure)
                        true
                    }
                    else -> false
                }
            }

            nameField.onTextChanged {
                adventure.name = it
            }
            descriptionField.onTextChanged {
                adventure.description = it
            }
            versionField.onTextChanged {
                adventure.version = it
            }
            authorField.onTextChanged {
                adventure.author = it
            }

            pointsList.let { list ->
                pointsListManager = PointsListManager(
                    requireContext(),
                    list,
                    idMaganerModel.idManager,
                    adventure::initialPoints,
                ) { item ->
                    backstack.goTo(PointTypeFormKey(item.id!!))
                }
            }

            adventureNodesList.let { list ->
                adventureNodesListManager = AdventureNodesListManager(
                    requireContext(),
                    list,
                    idMaganerModel.idManager,
                    adventure::adventureNodesList,
                ) {item ->
                    backstack.goTo(AdventureNodeFormKey(item.id!!))
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        with(binding) {
            nameField.setText(adventure.name)
            descriptionField.setText(adventure.description)
            versionField.setText(adventure.version)
            authorField.setText(adventure.author)

            adventure.initialPoints?.let { list ->
                pointsListManager.set(list)
            }

            adventure.adventureNodesList?.let { list ->
                adventureNodesListManager.set(list)
            }
        }
    }
}