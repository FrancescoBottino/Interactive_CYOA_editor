package it.thefreak.android.interactivecyoaeditor.ui.editor.forms.pointstate

import android.os.Bundle
import android.view.View
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import com.zhuinden.simplestackextensions.fragmentsktx.backstack
import com.zhuinden.simplestackextensions.fragmentsktx.lookup
import it.thefreak.android.interactivecyoaeditor.R
import it.thefreak.android.interactivecyoaeditor.databinding.PointTypeFormFragmentBinding
import it.thefreak.android.interactivecyoaeditor.hide
import it.thefreak.android.interactivecyoaeditor.model.PointType
import it.thefreak.android.interactivecyoaeditor.onTextChanged
import it.thefreak.android.interactivecyoaeditor.show
import it.thefreak.android.interactivecyoaeditor.ui.editor.components.RequirementsListManager
import it.thefreak.android.interactivecyoaeditor.ui.editor.forms.adventure.AdventureFormModel_idManager

class PointTypeFormFragment: KeyedFragment(R.layout.point_type_form_fragment) {
    private val idManagerModel by lazy { lookup<AdventureFormModel_idManager>() }

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
                    //todo open requirement form
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