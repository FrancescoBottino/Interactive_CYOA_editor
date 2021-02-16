package it.thefreak.android.interactivecyoaeditor.ui.editor.forms.costmodifier

import android.os.Bundle
import android.view.View
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import com.zhuinden.simplestackextensions.fragmentsktx.backstack
import com.zhuinden.simplestackextensions.fragmentsktx.lookup
import it.thefreak.android.interactivecyoaeditor.R
import it.thefreak.android.interactivecyoaeditor.databinding.CostModifierFormFragmentBinding
import it.thefreak.android.interactivecyoaeditor.model.ChoiceSelectionRequirement
import it.thefreak.android.interactivecyoaeditor.model.CostModifier
import it.thefreak.android.interactivecyoaeditor.model.PointAmountRequirement
import it.thefreak.android.interactivecyoaeditor.model.PointComparisonRequirement
import it.thefreak.android.interactivecyoaeditor.onTextChanged
import it.thefreak.android.interactivecyoaeditor.ui.editor.components.listmanagers.RequirementsListManager
import it.thefreak.android.interactivecyoaeditor.ui.editor.forms.adventure.AdventureFormModelIdManagerLoader
import it.thefreak.android.interactivecyoaeditor.ui.editor.forms.requirements.choiceselection.ChoiceSelectionRequirementFormKey
import it.thefreak.android.interactivecyoaeditor.ui.editor.forms.requirements.pointsamount.PointAmountRequirementFormKey
import it.thefreak.android.interactivecyoaeditor.ui.editor.forms.requirements.pointscomparison.PointComparisonRequirementFormKey

class CostModifierFormFragment: KeyedFragment(R.layout.cost_modifier_form_fragment) {
    private val idManagerModel by lazy { lookup<AdventureFormModelIdManagerLoader>() }

    private lateinit var binding: CostModifierFormFragmentBinding
    private lateinit var requirementsListManager: RequirementsListManager

    private lateinit var costModifier: CostModifier

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(getKey<CostModifierFormKey>()) {
            costModifier = idManagerModel.idManager.idMap[costModifierId] as CostModifier
        }

        binding = CostModifierFormFragmentBinding.bind(view)
        with(binding) {
            topAppBar.setNavigationOnClickListener {
                backstack.goBack()
            }

            costModAmountField.onTextChanged {
                costModifier.amount = it.toIntOrNull()
            }

            isHiddenSwitchField.setOnCheckedChangeListener { _, checked ->
                costModifier.hide = checked
            }

            requirementsList.let { list ->
                requirementsListManager = RequirementsListManager(
                        requireContext(),
                        list,
                        idManagerModel.idManager,
                        costModifier::requirements,
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
            costModifier.amount?.let {
                costModAmountField.setText(it.toString())
            }

            (costModifier.hide?:false).let {
                isHiddenSwitchField.isChecked = it
            }

            costModifier.requirements?.let { list ->
                requirementsListManager.set(list)
            }
        }
    }
}