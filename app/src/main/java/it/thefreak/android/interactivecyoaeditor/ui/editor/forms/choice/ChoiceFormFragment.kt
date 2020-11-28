package it.thefreak.android.interactivecyoaeditor.ui.editor.forms.choice

import android.os.Bundle
import android.view.View
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import com.zhuinden.simplestackextensions.fragmentsktx.backstack
import com.zhuinden.simplestackextensions.fragmentsktx.lookup
import it.thefreak.android.interactivecyoaeditor.R
import it.thefreak.android.interactivecyoaeditor.databinding.ChoiceFormFragmentBinding
import it.thefreak.android.interactivecyoaeditor.hide
import it.thefreak.android.interactivecyoaeditor.model.Choice
import it.thefreak.android.interactivecyoaeditor.onTextChanged
import it.thefreak.android.interactivecyoaeditor.show
import it.thefreak.android.interactivecyoaeditor.ui.editor.components.AdventureNodesListManager
import it.thefreak.android.interactivecyoaeditor.ui.editor.components.CostsListManager
import it.thefreak.android.interactivecyoaeditor.ui.editor.components.RequirementsListManager
import it.thefreak.android.interactivecyoaeditor.ui.editor.forms.adventure.AdventureFormModel_idManager
import it.thefreak.android.interactivecyoaeditor.ui.editor.forms.adventurenode.AdventureNodeFormKey
import it.thefreak.android.interactivecyoaeditor.ui.editor.forms.cost.CostFormKey

class ChoiceFormFragment: KeyedFragment(R.layout.choice_form_fragment) {
    private val idManagerModel by lazy { lookup<AdventureFormModel_idManager>() }

    private lateinit var binding: ChoiceFormFragmentBinding
    private lateinit var costsListManager: CostsListManager
    private lateinit var adventureNodesListManager: AdventureNodesListManager
    private lateinit var requirementsListManager: RequirementsListManager
    private lateinit var conditionsListManager: RequirementsListManager

    private lateinit var choice: Choice

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(getKey<ChoiceFormKey>()) {
            choice = idManagerModel.idManager.idMap[choiceId] as Choice
        }

        binding = ChoiceFormFragmentBinding.bind(view)
        with(binding) {
            topAppBar.setNavigationOnClickListener {
                backstack.goBack()
            }

            nameField.onTextChanged {
                choice.name = it
            }
            descriptionField.onTextChanged {
                choice.description = it
            }
            activatableSwitchField.setOnCheckedChangeListener { _, checked ->
                choice.activatable = checked

                if(checked) {
                    automaticallyActivatedSwitchField.hide()
                    conditionsList.hide()
                } else {
                    choice.automaticallyActivated?.let { auto ->
                        if(auto) {
                            conditionsList.show()
                        } else {
                            conditionsList.hide()
                        }
                    }
                    automaticallyActivatedSwitchField.show()
                }
            }
            automaticallyActivatedSwitchField.setOnCheckedChangeListener { _, checked ->
                choice.automaticallyActivated = checked

                choice.activatable?.let { activatable ->
                    if(!activatable && checked) {
                        conditionsList.show()
                    } else {
                        conditionsList.hide()
                    }
                }
            }
            conditionsList.let { list ->
                conditionsListManager = RequirementsListManager(
                        requireContext(),
                        list,
                        idManagerModel.idManager,
                        choice::conditions
                ) {
                    //todo open requirement form
                }
            }
            buyLimitField.onTextChanged {
                choice.buyLimit = it.toIntOrNull()
            }
            costsList.let { list ->
                costsListManager = CostsListManager(
                        requireContext(),
                        list,
                        idManagerModel.idManager,
                        choice::costs
                ) {
                    backstack.goTo(CostFormKey(it.id!!))
                }
            }
            subNodesList.let { list ->
                adventureNodesListManager = AdventureNodesListManager(
                        requireContext(),
                        list,
                        idManagerModel.idManager,
                        choice::subNodes
                ) {
                    backstack.goTo(AdventureNodeFormKey(it.id!!))
                }
            }
            isHiddenSwitchField.setOnCheckedChangeListener { _, checked ->
                choice.hide = checked
            }
            requirementsList.let { list ->
                requirementsListManager = RequirementsListManager(
                        requireContext(),
                        list,
                        idManagerModel.idManager,
                        choice::requirements,
                ) {
                    //todo open requirement form
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        with(binding) {
            nameField.setText(choice.name)

            descriptionField.setText(choice.description)

            (choice.activatable?:true).let { activatable ->
                activatableSwitchField.isChecked = activatable

                (choice.automaticallyActivated?:false).let { auto ->
                    automaticallyActivatedSwitchField.isChecked = auto

                    if(activatable) {
                        automaticallyActivatedSwitchField.hide()
                        conditionsList.hide()
                    } else {
                        if(auto) {
                            conditionsList.show()
                        } else {
                            conditionsList.hide()
                        }
                        automaticallyActivatedSwitchField.show()
                    }
                }
            }

            choice.conditions?.let { list ->
                conditionsListManager.set(list)
            }

            choice.buyLimit?.let {
                buyLimitField.setText(it.toString())
            }

            choice.costs?.let { list ->
                costsListManager.set(list)
            }

            choice.subNodes?.let { list ->
                adventureNodesListManager.set(list)
            }

            (choice.hide?:false).let {
                isHiddenSwitchField.isChecked = it
            }

            choice.requirements?.let { list ->
                requirementsListManager.set(list)
            }
        }
    }
}