package it.thefreak.android.interactivecyoaeditor.ui.editor.forms.adventurenode

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import com.tiper.MaterialSpinner
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import com.zhuinden.simplestackextensions.fragmentsktx.backstack
import com.zhuinden.simplestackextensions.fragmentsktx.lookup
import it.thefreak.android.interactivecyoaeditor.R
import it.thefreak.android.interactivecyoaeditor.databinding.AdventureNodeFormFragmentBinding
import it.thefreak.android.interactivecyoaeditor.hide
import it.thefreak.android.interactivecyoaeditor.model.*
import it.thefreak.android.interactivecyoaeditor.onTextChanged
import it.thefreak.android.interactivecyoaeditor.show
import it.thefreak.android.interactivecyoaeditor.ui.editor.components.ChoicesListManager
import it.thefreak.android.interactivecyoaeditor.ui.editor.components.PointsListManager
import it.thefreak.android.interactivecyoaeditor.ui.editor.components.RequirementsListManager
import it.thefreak.android.interactivecyoaeditor.ui.editor.forms.adventure.AdventureFormModelIdManagerLoader
import it.thefreak.android.interactivecyoaeditor.ui.editor.forms.choice.ChoiceFormKey
import it.thefreak.android.interactivecyoaeditor.ui.editor.forms.pointstate.PointTypeFormKey
import it.thefreak.android.interactivecyoaeditor.ui.editor.forms.requirements.choiceselection.ChoiceSelectionRequirementFormKey
import it.thefreak.android.interactivecyoaeditor.ui.editor.forms.requirements.pointsamount.PointAmountRequirementFormKey
import it.thefreak.android.interactivecyoaeditor.ui.editor.forms.requirements.pointscomparison.PointComparisonRequirementFormKey

class AdventureNodeFormFragment: KeyedFragment(R.layout.adventure_node_form_fragment) {
    private val idManagerModel by lazy { lookup<AdventureFormModelIdManagerLoader>() }

    private lateinit var binding: AdventureNodeFormFragmentBinding
    private lateinit var pointsListManager: PointsListManager
    private lateinit var choicesListManager: ChoicesListManager
    private lateinit var requirementsListManager: RequirementsListManager

    private lateinit var adventureNode: AdventureNode

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(getKey<AdventureNodeFormKey>()) {
            adventureNode = idManagerModel.idManager.idMap[adventureNodeId] as AdventureNode
        }

        binding = AdventureNodeFormFragmentBinding.bind(view)
        with(binding) {
            topAppBar.setNavigationOnClickListener {
                backstack.goBack()
            }

            nameField.onTextChanged {
                adventureNode.name = it
            }
            descriptionField.onTextChanged {
                adventureNode.description = it
            }
            hasChoicesSwitchField.setOnCheckedChangeListener { _, checked ->
                adventureNode.hasChoices = checked
                if(checked) {
                    choiceContentFields.show()
                } else {
                    choiceContentFields.hide()
                }
            }
            pointsList.let { list ->
                pointsListManager = PointsListManager(
                    requireContext(),
                    list,
                    idManagerModel.idManager,
                    adventureNode::nodeSpecificPoints,
                ) { item ->
                    backstack.goTo(PointTypeFormKey(item.id!!))
                }
            }
            choiceBuyLimitField.onTextChanged {
                adventureNode.choiceLimit = it.toIntOrNull()
            }
            choiceTypeSpinnerField.let { spinner ->
                ArrayAdapter.createFromResource(
                        requireContext(),
                        R.array.choice_type_values,
                        android.R.layout.simple_spinner_item
                ).also { adapter ->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner.adapter = adapter
                }
                spinner.onItemSelectedListener = object : MaterialSpinner.OnItemSelectedListener {
                    override fun onItemSelected(parent: MaterialSpinner, view: View?, position: Int, id: Long) {
                        adventureNode.choiceGroupType = ChoicesGroupType.values()[position]
                    }

                    override fun onNothingSelected(parent: MaterialSpinner) {
                        adventureNode.choiceGroupType = null
                    }
                }
            }
            choicesList.let { list ->
                choicesListManager = ChoicesListManager(
                    requireContext(),
                    list,
                    idManagerModel.idManager,
                    adventureNode::choicesList
                ) { item ->
                    backstack.goTo(ChoiceFormKey(item.id!!))
                }
            }
            isHiddenSwitchField.setOnCheckedChangeListener { _, checked ->
                adventureNode.hide = checked

                if(checked) {
                    requirementsList.show()
                } else {
                    requirementsList.hide()
                }
            }
            requirementsList.let { list ->
                requirementsListManager = RequirementsListManager(
                        requireContext(),
                        list,
                        idManagerModel.idManager,
                        adventureNode::requirements,
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
            nameField.setText(adventureNode.name)
            descriptionField.setText(adventureNode.description)
            (adventureNode.hasChoices?:false).let {
                hasChoicesSwitchField.isChecked = it
                if(it) {
                    choiceContentFields.show()
                } else {
                    choiceContentFields.hide()
                }
            }
            adventureNode.nodeSpecificPoints?.let { list ->
                pointsListManager.set(list)
            }
            adventureNode.choiceLimit?.let {
                choiceBuyLimitField.setText(it.toString())
            }
            adventureNode.choiceGroupType?.let { type ->
                choiceTypeSpinnerField.selection = type.ordinal
            }
            adventureNode.choicesList?.let { list ->
                choicesListManager.set(list)
            }
            (adventureNode.hide?:false).let {
                isHiddenSwitchField.isChecked = it
                if(it) {
                    requirementsList.show()
                } else {
                    requirementsList.hide()
                }
            }
            adventureNode.requirements?.let { list ->
                requirementsListManager.set(list)
            }
        }
    }
}