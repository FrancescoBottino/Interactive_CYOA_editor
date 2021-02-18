package it.thefreak.android.interactivecyoaeditor.ui.editor.forms.requirements.pointsamount

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import com.tiper.MaterialSpinner
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import com.zhuinden.simplestackextensions.fragmentsktx.backstack
import com.zhuinden.simplestackextensions.fragmentsktx.lookup
import it.thefreak.android.interactivecyoaeditor.R
import it.thefreak.android.interactivecyoaeditor.databinding.PointsAmountRequirementFormFragmentBinding
import it.thefreak.android.interactivecyoaeditor.getAdapterWrapper
import it.thefreak.android.interactivecyoaeditor.model.entities.ComparisonFunction
import it.thefreak.android.interactivecyoaeditor.model.entities.PointAmountRequirement
import it.thefreak.android.interactivecyoaeditor.model.entities.PointType
import it.thefreak.android.interactivecyoaeditor.onTextChanged
import it.thefreak.android.interactivecyoaeditor.ui.editor.forms.adventure.AdventureFormModelIdManagerLoader

class PointAmountRequirementFormFragment: KeyedFragment(R.layout.points_amount_requirement_form_fragment) {
    private val idManagerModel by lazy { lookup<AdventureFormModelIdManagerLoader>() }

    private lateinit var binding: PointsAmountRequirementFormFragmentBinding

    private lateinit var pointAmountRequirement: PointAmountRequirement
    private lateinit var pointTypes: List<PointType>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(getKey<PointAmountRequirementFormKey>()) {
            pointAmountRequirement = idManagerModel.idManager.idMap[pointAmountRequirementId] as PointAmountRequirement
        }

        binding = PointsAmountRequirementFormFragmentBinding.bind(view)
        with(binding) {
            topAppBar.setNavigationOnClickListener {
                backstack.goBack()
            }

            pointTypes = idManagerModel.idManager.findByType<PointType>().values.toList()

            pointTypeSpinnerField.let { spinner ->
                ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    pointTypes.getAdapterWrapper { "${it.name?:it.id}" }
                ).also { adapter ->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner.adapter = adapter
                }

                spinner.onItemSelectedListener = object : MaterialSpinner.OnItemSelectedListener {
                    override fun onItemSelected(parent: MaterialSpinner, view: View?, position: Int, id: Long) {
                        pointTypes[position].let { pt ->
                            pointAmountRequirement.pointTypeId = pt.id
                        }
                    }

                    override fun onNothingSelected(parent: MaterialSpinner) {
                        pointAmountRequirement.pointTypeId = null
                    }
                }
            }

            comparisonFunctionSpinnerField.let { spinner ->
                ArrayAdapter.createFromResource(
                    requireContext(),
                    R.array.comparison_function_values,
                    android.R.layout.simple_spinner_item
                ).also { adapter ->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner.adapter = adapter
                }
                spinner.onItemSelectedListener = object : MaterialSpinner.OnItemSelectedListener {
                    override fun onItemSelected(parent: MaterialSpinner, view: View?, position: Int, id: Long) {
                        pointAmountRequirement.comparisonFunction = ComparisonFunction.values()[position]
                    }

                    override fun onNothingSelected(parent: MaterialSpinner) {
                        pointAmountRequirement.comparisonFunction = null
                    }
                }
            }

            amountField.onTextChanged {
                pointAmountRequirement.amount = it.toIntOrNull()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        with(binding) {
            pointAmountRequirement.pointTypeId?.let { id ->
                pointTypeSpinnerField.selection = pointTypes.indexOf(
                    pointTypes.first { it.id == id }
                )
            }

            pointAmountRequirement.comparisonFunction?.let { comparisonFunction ->
                comparisonFunctionSpinnerField.selection = comparisonFunction.ordinal
            }

            pointAmountRequirement.amount?.let {
                amountField.setText(it.toString())
            }
        }
    }
}