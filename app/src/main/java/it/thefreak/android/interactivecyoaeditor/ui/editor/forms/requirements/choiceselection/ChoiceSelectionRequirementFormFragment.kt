package it.thefreak.android.interactivecyoaeditor.ui.editor.forms.requirements.choiceselection

import android.os.Bundle
import android.view.View
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import com.zhuinden.simplestackextensions.fragmentsktx.backstack
import com.zhuinden.simplestackextensions.fragmentsktx.lookup
import it.thefreak.android.interactivecyoaeditor.R
import it.thefreak.android.interactivecyoaeditor.databinding.ChoiceSelectionRequirementFormFragmentBinding
import it.thefreak.android.interactivecyoaeditor.model.Choice
import it.thefreak.android.interactivecyoaeditor.model.ChoiceSelectionRequirement
import it.thefreak.android.interactivecyoaeditor.ui.editor.binders.ChoiceRequirementBinder
import it.thefreak.android.interactivecyoaeditor.ui.editor.components.ChoiceRequirementListManager
import it.thefreak.android.interactivecyoaeditor.ui.editor.components.Chooser
import it.thefreak.android.interactivecyoaeditor.ui.editor.forms.adventure.AdventureFormModel_idManager

class ChoiceSelectionRequirementFormFragment: KeyedFragment(R.layout.choice_selection_requirement_form_fragment) {
    private val idManagerModel by lazy { lookup<AdventureFormModel_idManager>() }

    private lateinit var binding: ChoiceSelectionRequirementFormFragmentBinding
    private lateinit var choiceRequirementListManager: ChoiceRequirementListManager
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
            idManagerModel.idManager,
            ChoiceRequirementBinder::selectionBinding
        )

        binding = ChoiceSelectionRequirementFormFragmentBinding.bind(view)
        with(binding) {
            topAppBar.setNavigationOnClickListener {
                backstack.goBack()
            }

            groupingTypeSpinnerField.let {
                //TODO
            }
            choiceRequirementList.let { list ->
                choiceRequirementListManager = ChoiceRequirementListManager(
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
            //TODO
            groupingTypeSpinnerField.apply {

            }
            choiceSelectionRequirement.choicesIds?.let { idsList ->
                choiceRequirementListManager.set(idsList.map { idManagerModel.idManager.idMap[it] as Choice })
            }
        }
    }
}