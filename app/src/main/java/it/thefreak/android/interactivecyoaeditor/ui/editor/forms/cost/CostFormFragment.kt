package it.thefreak.android.interactivecyoaeditor.ui.editor.forms.cost

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import com.tiper.MaterialSpinner
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import com.zhuinden.simplestackextensions.fragmentsktx.backstack
import com.zhuinden.simplestackextensions.fragmentsktx.lookup
import it.thefreak.android.interactivecyoaeditor.R
import it.thefreak.android.interactivecyoaeditor.databinding.CostFormFragmentBinding
import it.thefreak.android.interactivecyoaeditor.getAdapterWrapper
import it.thefreak.android.interactivecyoaeditor.model.Cost
import it.thefreak.android.interactivecyoaeditor.model.PointType
import it.thefreak.android.interactivecyoaeditor.onTextChanged
import it.thefreak.android.interactivecyoaeditor.ui.editor.components.CostModifiersListManager
import it.thefreak.android.interactivecyoaeditor.ui.editor.forms.adventure.AdventureFormModel_idManager
import it.thefreak.android.interactivecyoaeditor.ui.editor.forms.costmodifier.CostModifierFormKey

class CostFormFragment: KeyedFragment(R.layout.cost_form_fragment) {
    private val idManagerModel by lazy { lookup<AdventureFormModel_idManager>() }

    private lateinit var binding: CostFormFragmentBinding
    private lateinit var costModifiersListManager: CostModifiersListManager

    private lateinit var cost: Cost
    private lateinit var pointTypes: List<PointType>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(getKey<CostFormKey>()) {
            cost = idManagerModel.idManager.idMap[costId] as Cost
        }

        binding = CostFormFragmentBinding.bind(view)
        with(binding) {
            topAppBar.setNavigationOnClickListener {
                backstack.goBack()
            }

            pointTypeSpinnerField.let { spinner ->
                pointTypes = idManagerModel.idManager.findByType<PointType>().values.toList()

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
                            cost.pointTypeId = pt.id
                        }
                    }

                    override fun onNothingSelected(parent: MaterialSpinner) {
                        cost.pointTypeId = null
                    }
                }
            }

            amountField.onTextChanged {
                cost.amount = it.toIntOrNull()
            }

            isHiddenSwitchField.setOnCheckedChangeListener { _, checked ->
                cost.hide = checked
            }

            costModifiersList.let { list ->
                costModifiersListManager = CostModifiersListManager(
                        requireContext(),
                        list,
                        idManagerModel.idManager,
                        cost::modifiers,
                ) { item ->
                    backstack.goTo(CostModifierFormKey(item.id!!))
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        with(binding) {

            cost.pointTypeId?.let { id ->
                pointTypeSpinnerField.selection = pointTypes.indexOf(
                        pointTypes.first { it.id == id }
                )
            }

            cost.amount?.let {
                amountField.setText(it.toString())
            }

            (cost.hide?:false).let {
                isHiddenSwitchField.isChecked = it
            }

            cost.modifiers?.let { list ->
                costModifiersListManager.set(list)
            }
        }
    }
}