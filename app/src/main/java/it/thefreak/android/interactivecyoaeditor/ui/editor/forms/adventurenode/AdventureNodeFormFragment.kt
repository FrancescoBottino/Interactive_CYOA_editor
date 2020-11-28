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
import it.thefreak.android.interactivecyoaeditor.model.AdventureNode
import it.thefreak.android.interactivecyoaeditor.model.ChoicesGroupType
import it.thefreak.android.interactivecyoaeditor.onTextChanged
import it.thefreak.android.interactivecyoaeditor.show
import it.thefreak.android.interactivecyoaeditor.ui.editor.components.ChoicesListManager
import it.thefreak.android.interactivecyoaeditor.ui.editor.components.PointsListManager
import it.thefreak.android.interactivecyoaeditor.ui.editor.forms.adventure.AdventureFormModel_idManager
import it.thefreak.android.interactivecyoaeditor.ui.editor.forms.choice.ChoiceFormKey
import it.thefreak.android.interactivecyoaeditor.ui.editor.forms.pointstate.PointTypeFormKey

class AdventureNodeFormFragment: KeyedFragment(R.layout.adventure_node_form_fragment) {
    private val idManagerModel by lazy { lookup<AdventureFormModel_idManager>() }

    private lateinit var binding: AdventureNodeFormFragmentBinding
    private lateinit var pointsListManager: PointsListManager
    private lateinit var choicesListManager: ChoicesListManager

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
                    context,
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
                        R.array.choice_types_values,
                        android.R.layout.simple_spinner_item
                ).also { adapter ->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner.adapter = adapter
                }
                spinner.onItemSelectedListener = object : MaterialSpinner.OnItemSelectedListener {
                    override fun onItemSelected(parent: MaterialSpinner, view: View?, position: Int, id: Long) {
                        //0 optional, 1 required
                        when(position) {
                            0 -> adventureNode.choiceGroupType = ChoicesGroupType.OPTIONAL
                            1 -> adventureNode.choiceGroupType = ChoicesGroupType.REQUIRED
                            else -> throw Exception()
                        }
                    }

                    override fun onNothingSelected(parent: MaterialSpinner) {
                        adventureNode.choiceGroupType = null
                    }
                }
            }
            choicesList.let { list ->
                choicesListManager = ChoicesListManager(
                    context,
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
                when(type) {
                    ChoicesGroupType.OPTIONAL -> choiceTypeSpinnerField.selection = 0
                    ChoicesGroupType.REQUIRED -> choiceTypeSpinnerField.selection = 1
                }
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
        }
    }
}