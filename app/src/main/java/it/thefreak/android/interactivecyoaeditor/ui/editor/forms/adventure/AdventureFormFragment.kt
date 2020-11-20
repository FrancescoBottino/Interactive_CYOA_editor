package it.thefreak.android.interactivecyoaeditor.ui.editor.forms.adventure

import android.os.Bundle
import android.view.View
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import com.zhuinden.simplestackextensions.fragmentsktx.backstack
import com.zhuinden.simplestackextensions.fragmentsktx.lookup
import it.thefreak.android.interactivecyoaeditor.R
import it.thefreak.android.interactivecyoaeditor.databinding.AdventureFormFragmentBinding
import it.thefreak.android.interactivecyoaeditor.model.*
import it.thefreak.android.interactivecyoaeditor.onTextChanged
import it.thefreak.android.interactivecyoaeditor.ui.editor.components.AdventureNodesListManager
import it.thefreak.android.interactivecyoaeditor.ui.editor.components.ItemsListEditorListener
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

            pointsListManager = PointsListManager(
                context,
                pointsList,
                object : ItemsListEditorListener<PointType> {
                    override fun onItemDelete(item: PointType): Boolean {
                        item.deepDeleteItem(adventureFormModel.idManager, adventure::initialPoints)
                        return true
                    }

                    override fun onItemClick(item: PointType) {
                        backstack.goTo(PointTypeFormKey(item.id!!))
                    }

                    override fun onItemCopy(item: PointType): PointType? {
                        return item.deepCopyItem(adventureFormModel.idManager).apply {
                            (adventure::initialPoints).init().add(this)
                        }
                    }

                    override fun onNewItem(): PointType? {
                        return PointType().apply {
                            assignNewId(adventureFormModel.idManager)
                            (adventure::initialPoints).init().add(this)
                        }
                    }
                }
            )

            adventureItemsListManager = AdventureNodesListManager(
                context,
                adventureNodesList,
                object : ItemsListEditorListener<AdventureNode> {
                    override fun onItemDelete(item: AdventureNode): Boolean {
                        item.deepDeleteItem(adventureFormModel.idManager, adventure.adventureNodesList)
                        return true
                    }

                    override fun onItemClick(item: AdventureNode) {
                        backstack.goTo(AdventureNodeFormKey(item.id!!))
                    }

                    override fun onItemCopy(item: AdventureNode): AdventureNode? {
                        return item.deepCopyItem(adventureFormModel.idManager).apply {
                            (adventure::adventureNodesList).init().add(this)
                        }
                    }

                    override fun onNewItem(): AdventureNode? {
                        return AdventureNode().apply {
                            assignNewId(adventureFormModel.idManager)
                            (adventure::adventureNodesList).init().add(this)
                        }
                    }
                }
            )
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