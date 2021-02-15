package it.thefreak.android.interactivecyoaeditor.ui.editor.forms.requirements.choiceselection

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import com.tiper.MaterialSpinner
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import com.zhuinden.simplestackextensions.fragmentsktx.backstack
import com.zhuinden.simplestackextensions.fragmentsktx.lookup
import it.thefreak.android.interactivecyoaeditor.R
import it.thefreak.android.interactivecyoaeditor.databinding.ChoiceSelectionRequirementFormFragmentBinding
import it.thefreak.android.interactivecyoaeditor.model.Choice
import it.thefreak.android.interactivecyoaeditor.model.ChoiceSelectionRequirement
import it.thefreak.android.interactivecyoaeditor.model.GroupingFunction
import it.thefreak.android.interactivecyoaeditor.ui.editor.binders.ChoiceRequirementBinder
import it.thefreak.android.interactivecyoaeditor.ui.editor.components.ChoiceSelectionRequirementListManager
import it.thefreak.android.interactivecyoaeditor.ui.editor.components.Chooser
import it.thefreak.android.interactivecyoaeditor.ui.editor.forms.adventure.AdventureFormModelIdManagerLoader

class ChoiceSelectionRequirementFormFragment: KeyedFragment(R.layout.choice_selection_requirement_form_fragment) {
    private val idManagerModel by lazy { lookup<AdventureFormModelIdManagerLoader>() }

    private lateinit var binding: ChoiceSelectionRequirementFormFragmentBinding
    private lateinit var choiceSelectionRequirementListManager: ChoiceSelectionRequirementListManager
    private lateinit var chooser: Chooser<Choice>

    private lateinit var choiceSelectionRequirement: ChoiceSelectionRequirement

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(getKey<ChoiceSelectionRequirementFormKey>()) {
            choiceSelectionRequirement = idManagerModel.idManager.idMap[choiceSelectionRequirementId] as ChoiceSelectionRequirement
        }

        chooser = Chooser(
            requireContext(),
            Choice::class,
            R.string.choice_selection_requirement_dialog_title,
            idManagerModel.idManager,
            ChoiceRequirementBinder::selectionBinding
        )

        binding = ChoiceSelectionRequirementFormFragmentBinding.bind(view)
        with(binding) {
            topAppBar.setNavigationOnClickListener {
                backstack.goBack()
            }

            groupingTypeSpinnerField.let { spinner ->
                ArrayAdapter.createFromResource(
                    requireContext(),
                    R.array.grouping_function_values,
                    android.R.layout.simple_spinner_item
                ).also { adapter ->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner.adapter = adapter
                }
                spinner.onItemSelectedListener = object : MaterialSpinner.OnItemSelectedListener {
                    override fun onItemSelected(parent: MaterialSpinner, view: View?, position: Int, id: Long) {
                        choiceSelectionRequirement.groupingFunction = GroupingFunction.values()[position]
                    }

                    override fun onNothingSelected(parent: MaterialSpinner) {
                        choiceSelectionRequirement.groupingFunction = null
                    }
                }
            }

            choiceRequirementList.let { list ->
                choiceSelectionRequirementListManager = ChoiceSelectionRequirementListManager(
                    requireContext(),
                    list,
                    choiceSelectionRequirement::choicesIds,
                    chooser
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        with(binding) {
            choiceSelectionRequirement.groupingFunction?.let { function ->
                groupingTypeSpinnerField.selection = function.ordinal
            }

            choiceSelectionRequirement.choicesIds?.let { idsList ->
                choiceSelectionRequirementListManager.set(
                    idsList.map {
                        idManagerModel.idManager.idMap[it] as Choice
                    }
                )
            }
        }
    }
}