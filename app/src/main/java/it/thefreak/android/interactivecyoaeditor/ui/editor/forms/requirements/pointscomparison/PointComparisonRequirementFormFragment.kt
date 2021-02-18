package it.thefreak.android.interactivecyoaeditor.ui.editor.forms.requirements.pointscomparison

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import com.tiper.MaterialSpinner
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import com.zhuinden.simplestackextensions.fragmentsktx.backstack
import com.zhuinden.simplestackextensions.fragmentsktx.lookup
import it.thefreak.android.interactivecyoaeditor.R
import it.thefreak.android.interactivecyoaeditor.databinding.PointsComparisonRequirementFormFragmentBinding
import it.thefreak.android.interactivecyoaeditor.getAdapterWrapper
import it.thefreak.android.interactivecyoaeditor.model.entities.ComparisonFunction
import it.thefreak.android.interactivecyoaeditor.model.entities.PointComparisonRequirement
import it.thefreak.android.interactivecyoaeditor.model.entities.PointType
import it.thefreak.android.interactivecyoaeditor.ui.editor.forms.adventure.AdventureFormModelIdManagerLoader

class PointComparisonRequirementFormFragment: KeyedFragment(R.layout.points_comparison_requirement_form_fragment) {
    private val idManagerModel by lazy { lookup<AdventureFormModelIdManagerLoader>() }

    private lateinit var binding: PointsComparisonRequirementFormFragmentBinding

    private lateinit var pointComparisonRequirement: PointComparisonRequirement
    private lateinit var pointTypes: List<PointType>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(getKey<PointComparisonRequirementFormKey>()) {
            pointComparisonRequirement = idManagerModel.idManager.idMap[pointComparisonRequirementId] as PointComparisonRequirement
        }

        binding = PointsComparisonRequirementFormFragmentBinding.bind(view)
        with(binding) {
            topAppBar.setNavigationOnClickListener {
                backstack.goBack()
            }

            pointTypes = idManagerModel.idManager.findByType<PointType>().values.toList()

            pointTypeASpinnerField.let { spinner ->
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
                            pointComparisonRequirement.pointTypeAId = pt.id
                        }
                    }

                    override fun onNothingSelected(parent: MaterialSpinner) {
                        pointComparisonRequirement.pointTypeAId = null
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
                        pointComparisonRequirement.comparisonFunction = ComparisonFunction.values()[position]
                    }

                    override fun onNothingSelected(parent: MaterialSpinner) {
                        pointComparisonRequirement.comparisonFunction = null
                    }
                }
            }

            pointTypeBSpinnerField.let { spinner ->
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
                            pointComparisonRequirement.pointTypeBId = pt.id
                        }
                    }

                    override fun onNothingSelected(parent: MaterialSpinner) {
                        pointComparisonRequirement.pointTypeBId = null
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        with(binding) {
            pointComparisonRequirement.pointTypeAId?.let { id ->
                pointTypeASpinnerField.selection = pointTypes.indexOf(
                    pointTypes.first { it.id == id }
                )
            }

            pointComparisonRequirement.comparisonFunction?.let { comparisonFunction ->
                comparisonFunctionSpinnerField.selection = comparisonFunction.ordinal
            }

            pointComparisonRequirement.pointTypeBId?.let { id ->
                pointTypeBSpinnerField.selection = pointTypes.indexOf(
                    pointTypes.first { it.id == id }
                )
            }
        }
    }
}