package it.thefreak.android.interactivecyoaeditor.ui.editor.forms.pointstate

import android.os.Bundle
import android.view.View
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import com.zhuinden.simplestackextensions.fragmentsktx.lookup
import it.thefreak.android.interactivecyoaeditor.R
import it.thefreak.android.interactivecyoaeditor.databinding.PointStateFormFragmentBinding
import it.thefreak.android.interactivecyoaeditor.model.PointState
import it.thefreak.android.interactivecyoaeditor.onTextChanged
import it.thefreak.android.interactivecyoaeditor.ui.editor.forms.adventure.AdventureFormModel

class PointStateFormFragment: KeyedFragment(R.layout.point_state_form_fragment) {
    private val adventureFormModel by lazy { lookup<AdventureFormModel>() }

    private lateinit var binding: PointStateFormFragmentBinding

    private lateinit var pointState: PointState

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(getKey<PointStateFormKey>()) {
            pointState = adventureFormModel.idManager.idMap[pointStateId] as PointState
        }

        binding = PointStateFormFragmentBinding.bind(view)
        with(binding) {
            nameField.onTextChanged {
                pointState.name = it
            }
            descriptionField.onTextChanged {
                pointState.description = it
            }
            initialAmountField.onTextChanged {
                if(it.isNotBlank())
                    pointState.amount = it.toInt()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        with(binding) {
            nameField.setText(pointState.name)
            descriptionField.setText(pointState.description)
            pointState.amount?.let {
                initialAmountField.setText(it.toString())
            }
        }
    }
}