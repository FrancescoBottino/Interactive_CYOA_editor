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
import it.thefreak.android.interactivecyoaeditor.ui.editor.forms.pointstate.PointStateFormKey

class AdventureFormFragment: KeyedFragment(R.layout.adventure_form_fragment) {
    private val adventureFormModel by lazy { lookup<AdventureFormModel>() }

    private lateinit var binding: AdventureFormFragmentBinding
    private lateinit var pointsListManager: PointsListManager
    private lateinit var adventureItemsListManager: AdventureNodesListManager

    private lateinit var adventure: Adventure

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adventure = adventureFormModel.adventure

        binding = AdventureFormFragmentBinding.bind(view)
        with(binding) {
            topAppBar.setNavigationOnClickListener {
                backstack.goBack()
            }

            topAppBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.editor_menu_save_action -> {
                        //TODO handle save action
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
                object : ItemsListEditorListener<PointState> {
                    override fun onItemDelete(item: PointState): Boolean {
                        item.unregister(adventureFormModel.idManager, adventure::initialPoints)
                        return true
                    }

                    override fun onItemClick(item: PointState) {
                        backstack.goTo(PointStateFormKey(item.id!!))
                    }

                    override fun onItemCopy(item: PointState): PointState? {
                        return PointState().apply {
                            deepCopy(item, adventureFormModel.idManager)
                            this.insertIntoContainer(adventure::initialPoints)
                        }
                    }

                    override fun onNewItem(): PointState? {
                        return PointState().apply {
                            register(adventureFormModel.idManager, adventure::initialPoints)
                        }
                    }
                }
            )

            adventureItemsListManager = AdventureNodesListManager(
                context,
                adventureNodesList,
                object : ItemsListEditorListener<AdventureNode> {
                    override fun onItemDelete(item: AdventureNode): Boolean {
                        item.unregister(adventureFormModel.idManager, adventure::adventureNodesList)
                        return true
                    }

                    override fun onItemClick(item: AdventureNode) {
                        //TODO OPEN FRAGMENT
                    }

                    override fun onItemCopy(item: AdventureNode): AdventureNode? {
                        return AdventureNode().apply {
                            deepCopy(item, adventureFormModel.idManager)
                            this.insertIntoContainer(adventure::adventureNodesList)
                        }
                    }

                    override fun onNewItem(): AdventureNode? {
                        return AdventureNode().apply {
                            register(adventureFormModel.idManager, adventure::adventureNodesList)
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