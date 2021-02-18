package it.thefreak.android.interactivecyoaeditor.ui.editor.forms.pointstate

import android.os.Bundle
import android.view.View
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import com.zhuinden.simplestackextensions.fragmentsktx.backstack
import com.zhuinden.simplestackextensions.fragmentsktx.lookup
import it.thefreak.android.interactivecyoaeditor.R
import it.thefreak.android.interactivecyoaeditor.databinding.PointTypeFormFragmentBinding
import it.thefreak.android.interactivecyoaeditor.hide
import it.thefreak.android.interactivecyoaeditor.model.entities.ChoiceSelectionRequirement
import it.thefreak.android.interactivecyoaeditor.model.entities.PointAmountRequirement
import it.thefreak.android.interactivecyoaeditor.model.entities.PointComparisonRequirement
import it.thefreak.android.interactivecyoaeditor.model.entities.PointType
import it.thefreak.android.interactivecyoaeditor.onTextChanged
import it.thefreak.android.interactivecyoaeditor.show
import it.thefreak.android.interactivecyoaeditor.ui.editor.components.listmanagers.RequirementsListManager
import it.thefreak.android.interactivecyoaeditor.ui.editor.forms.adventure.AdventureFormModelIdManagerLoader
import it.thefreak.android.interactivecyoaeditor.ui.editor.forms.requirements.choiceselection.ChoiceSelectionRequirementFormKey
import it.thefreak.android.interactivecyoaeditor.ui.editor.forms.requirements.pointsamount.PointAmountRequirementFormKey
import it.thefreak.android.interactivecyoaeditor.ui.editor.forms.requirements.pointscomparison.PointComparisonRequirementFormKey

class PointTypeFormFragment: KeyedFragment(R.layout.point_type_form_fragment) {
    private val idManagerModel by lazy { lookup<AdventureFormModelIdManagerLoader>() }

    private lateinit var binding: PointTypeFormFragmentBinding
    private lateinit var requirementsListManager: RequirementsListManager

    private lateinit var pointType: PointType

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(getKey<PointTypeFormKey>()) {
            pointType = idManagerModel.idManager.idMap[pointTypeId] as PointType
        }

        binding = PointTypeFormFragmentBinding.bind(view)
        with(binding) {
            topAppBar.setNavigationOnClickListener {
                backstack.goBack()
            }

            nameField.onTextChanged {
                pointType.name = it
            }
            descriptionField.onTextChanged {
                pointType.description = it
            }
            initialAmountField.onTextChanged {
                pointType.initialAmount = it.toIntOrNull()
            }
            canGoUnderZeroSwitchField.setOnCheckedChangeListener { _, checked ->
                pointType.canGoUnderZero = checked
            }
            isHiddenSwitchField.setOnCheckedChangeListener { _, checked ->
                pointType.hide = checked

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
                        pointType::requirements,
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
            nameField.setText(pointType.name)
            descriptionField.setText(pointType.description)
            pointType.initialAmount?.let {
                initialAmountField.setText(it.toString())
            }
            (pointType.canGoUnderZero?:false).let {
                canGoUnderZeroSwitchField.isChecked = it
            }
            (pointType.hide?:false).let {
                isHiddenSwitchField.isChecked = it
                if(it) {
                    requirementsList.show()
                } else {
                    requirementsList.hide()
                }
            }
            pointType.requirements?.let { list ->
                requirementsListManager.set(list)
            }
        }
    }
}