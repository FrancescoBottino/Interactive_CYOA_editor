package it.thefreak.android.interactivecyoaeditor.ui.editor.forms.costmodifiers.additivecostmodifier

import android.os.Bundle
import android.view.View
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import com.zhuinden.simplestackextensions.fragmentsktx.backstack
import com.zhuinden.simplestackextensions.fragmentsktx.lookup
import it.thefreak.android.interactivecyoaeditor.R
import it.thefreak.android.interactivecyoaeditor.databinding.AdditiveCostModifierFormFragmentBinding
import it.thefreak.android.interactivecyoaeditor.model.entities.AdditiveCostModifier
import it.thefreak.android.interactivecyoaeditor.model.entities.ChoiceSelectionRequirement
import it.thefreak.android.interactivecyoaeditor.model.entities.PointAmountRequirement
import it.thefreak.android.interactivecyoaeditor.model.entities.PointComparisonRequirement
import it.thefreak.android.interactivecyoaeditor.onTextChanged
import it.thefreak.android.interactivecyoaeditor.ui.editor.components.listmanagers.RequirementsListManager
import it.thefreak.android.interactivecyoaeditor.ui.editor.forms.adventure.AdventureFormModelIdManagerLoader
import it.thefreak.android.interactivecyoaeditor.ui.editor.forms.requirements.choiceselection.ChoiceSelectionRequirementFormKey
import it.thefreak.android.interactivecyoaeditor.ui.editor.forms.requirements.pointsamount.PointAmountRequirementFormKey
import it.thefreak.android.interactivecyoaeditor.ui.editor.forms.requirements.pointscomparison.PointComparisonRequirementFormKey

class AdditiveCostModifierFormFragment: KeyedFragment(R.layout.additive_cost_modifier_form_fragment) {
    private val idManagerModel by lazy { lookup<AdventureFormModelIdManagerLoader>() }

    private lateinit var binding: AdditiveCostModifierFormFragmentBinding
    private lateinit var requirementsListManager: RequirementsListManager

    private lateinit var costModifier: AdditiveCostModifier

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(getKey<AdditiveCostModifierFormKey>()) {
            costModifier = idManagerModel.idManager.idMap[costModifierId] as AdditiveCostModifier
        }

        binding = AdditiveCostModifierFormFragmentBinding.bind(view)
        with(binding) {
            topAppBar.setNavigationOnClickListener {
                backstack.goBack()
            }

            costModAmountField.onTextChanged {
                costModifier.amountInt = it.toIntOrNull()
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
            costModifier.amountInt?.let {
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