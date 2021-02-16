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
import it.thefreak.android.interactivecyoaeditor.model.ChoiceSelectionRequirement
import it.thefreak.android.interactivecyoaeditor.model.PointAmountRequirement
import it.thefreak.android.interactivecyoaeditor.model.PointComparisonRequirement
import it.thefreak.android.interactivecyoaeditor.onTextChanged
import it.thefreak.android.interactivecyoaeditor.show
import it.thefreak.android.interactivecyoaeditor.ui.editor.components.listmanagers.AdventureNodesListManager
import it.thefreak.android.interactivecyoaeditor.ui.editor.components.listmanagers.CostsListManager
import it.thefreak.android.interactivecyoaeditor.ui.editor.components.listmanagers.RequirementsListManager
import it.thefreak.android.interactivecyoaeditor.ui.editor.forms.adventure.AdventureFormModelIdManagerLoader
import it.thefreak.android.interactivecyoaeditor.ui.editor.forms.adventurenode.AdventureNodeFormKey
import it.thefreak.android.interactivecyoaeditor.ui.editor.forms.cost.CostFormKey
import it.thefreak.android.interactivecyoaeditor.ui.editor.forms.requirements.choiceselection.ChoiceSelectionRequirementFormKey
import it.thefreak.android.interactivecyoaeditor.ui.editor.forms.requirements.pointsamount.PointAmountRequirementFormKey
import it.thefreak.android.interactivecyoaeditor.ui.editor.forms.requirements.pointscomparison.PointComparisonRequirementFormKey

class ChoiceFormFragment: KeyedFragment(R.layout.choice_form_fragment) {
    private val idManagerModel by lazy { lookup<AdventureFormModelIdManagerLoader>() }

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
                    when(it) {
                        is PointAmountRequirement ->
                            backstack.goTo(PointAmountRequirementFormKey(it.id!!))
                        is PointComparisonRequirement ->
                            backstack.goTo(PointComparisonRequirementFormKey(it.id!!))
                        is ChoiceSelectionRequirement ->
                            backstack.goTo(ChoiceSelectionRequirementFormKey(it.id!!))
                    }
                }
            }
            multiBuySwitchField.setOnCheckedChangeListener { _, checked ->
                choice.multiBuy = checked

                if(checked) {
                    buyLimitFieldLayout.show()
                } else {
                    buyLimitFieldLayout.hide()
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
                    when(it) {
                        is PointAmountRequirement ->
                            backstack.goTo(PointAmountRequirementFormKey(it.id!!))
                        is PointComparisonRequirement ->
                            backstack.goTo(PointComparisonRequirementFormKey(it.id!!))
                        is ChoiceSelectionRequirement ->
                            backstack.goTo(ChoiceSelectionRequirementFormKey(it.id!!))
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        with(binding) {
            nameField.setText(choice.name)

            descriptionField.setText(choice.description)

            val activatable = (choice.activatable?:true)
            activatableSwitchField.isChecked = activatable

            val autoActive = (choice.automaticallyActivated?:false)
            automaticallyActivatedSwitchField.isChecked = autoActive

            if(activatable) {
                automaticallyActivatedSwitchField.hide()
                conditionsList.hide()
            } else {
                if(autoActive) {
                    conditionsList.show()
                } else {
                    conditionsList.hide()
                }
                automaticallyActivatedSwitchField.show()
            }

            choice.conditions?.let { list ->
                conditionsListManager.set(list)
            }

            (choice.multiBuy?:false).let { multi ->
                multiBuySwitchField.isChecked = multi
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