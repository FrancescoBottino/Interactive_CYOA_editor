package it.thefreak.android.interactivecyoaeditor.ui.editor.forms.adventure

import android.os.Bundle
import android.view.View
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import com.zhuinden.simplestackextensions.fragmentsktx.backstack
import com.zhuinden.simplestackextensions.fragmentsktx.lookup
import it.thefreak.android.interactivecyoaeditor.R
import it.thefreak.android.interactivecyoaeditor.databinding.AdventureFormFragmentBinding
import it.thefreak.android.interactivecyoaeditor.model.Adventure
import it.thefreak.android.interactivecyoaeditor.onTextChanged
import it.thefreak.android.interactivecyoaeditor.ui.editor.components.AdventureNodesListManager
import it.thefreak.android.interactivecyoaeditor.ui.editor.components.PointsListManager
import it.thefreak.android.interactivecyoaeditor.ui.editor.forms.adventurenode.AdventureNodeFormKey
import it.thefreak.android.interactivecyoaeditor.ui.editor.forms.pointstate.PointTypeFormKey

class AdventureFormFragment: KeyedFragment(R.layout.adventure_form_fragment) {
    private val adventureFormModel by lazy { lookup<AdventureFormModel>() }

    private lateinit var binding: AdventureFormFragmentBinding
    private lateinit var pointsListManager: PointsListManager
    private lateinit var adventureItemsListManager: AdventureNodesListManager

    private lateinit var adventure: Adventure

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adventure = adventureFormModel.getAdventure(requireContext())

        binding = AdventureFormFragmentBinding.bind(view)
        with(binding) {
            topAppBar.setNavigationOnClickListener {
                backstack.goBack()
            }

            topAppBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.editor_menu_save_action -> {
                        //TODO handle save action
                        //MOCK
                        saveAction(requireContext(), adventure)
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
            versionNameField.onTextChanged {
                adventure.versionName = it
            }
            versionCodeField.onTextChanged {
                if(it.isNotBlank())
                    adventure.versionCode = it.toInt()
            }
            authorField.onTextChanged {
                adventure.author = it
            }

            pointsList.let { list ->
                pointsListManager = PointsListManager(
                    context,
                    list,
                    adventureFormModel.idManager,
                    adventure::initialPoints,
                ) { item ->
                    backstack.goTo(PointTypeFormKey(item.id!!))
                }
            }

            adventureNodesList.let { list ->
                adventureItemsListManager = AdventureNodesListManager(
                    context,
                    list,
                    adventureFormModel.idManager,
                    adventure::adventureNodesList,
                ) {item ->
                    backstack.goTo(AdventureNodeFormKey(item.id!!))
                }
            }
            adventureItemsListManager = AdventureNodesListManager(
                context,
                adventureNodesList,
                adventureFormModel.idManager,
                adventure::adventureNodesList,
            ) {item ->
                backstack.goTo(AdventureNodeFormKey(item.id!!))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        with(binding) {
            nameField.setText(adventure.name)
            descriptionField.setText(adventure.description)
            versionNameField.setText(adventure.versionName)
            adventure.versionCode?.let {
                versionCodeField.setText(it.toString())
            }
            authorField.setText(adventure.author)

            adventure.initialPoints?.let { list ->
                pointsListManager.set(list)
            }

            adventure.adventureNodesList?.let { list ->
                adventureItemsListManager.set(list)
            }
        }
    }
}