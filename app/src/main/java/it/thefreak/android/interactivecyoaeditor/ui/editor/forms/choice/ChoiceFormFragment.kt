package it.thefreak.android.interactivecyoaeditor.ui.editor.forms.choice

import android.os.Bundle
import android.view.View
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import com.zhuinden.simplestackextensions.fragmentsktx.backstack
import com.zhuinden.simplestackextensions.fragmentsktx.lookup
import it.thefreak.android.interactivecyoaeditor.R
import it.thefreak.android.interactivecyoaeditor.databinding.ChoiceFormFragmentBinding
import it.thefreak.android.interactivecyoaeditor.model.Choice
import it.thefreak.android.interactivecyoaeditor.onTextChanged
import it.thefreak.android.interactivecyoaeditor.ui.editor.components.AdventureNodesListManager
import it.thefreak.android.interactivecyoaeditor.ui.editor.components.CostsListManager
import it.thefreak.android.interactivecyoaeditor.ui.editor.forms.adventure.AdventureFormModel
import it.thefreak.android.interactivecyoaeditor.ui.editor.forms.adventurenode.AdventureNodeFormKey

class ChoiceFormFragment: KeyedFragment(R.layout.choice_form_fragment) {
    private val adventureFormModel by lazy { lookup<AdventureFormModel>() }

    private lateinit var binding: ChoiceFormFragmentBinding
    private lateinit var costsListManager: CostsListManager
    private lateinit var adventureNodesListManager: AdventureNodesListManager

    private lateinit var choice: Choice

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(getKey<ChoiceFormKey>()) {
            choice = adventureFormModel.idManager.idMap[choiceId] as Choice
        }

        binding = ChoiceFormFragmentBinding.bind(view)
        with(binding) {
            topAppBar.setNavigationOnClickListener {
                backstack.goBack()
            }

            nameField.onTextChanged {
                choice.name = it
            }
            descriptionField.onTextChanged {
                choice.description = it
            }
            automaticallyActivatedSwitchField.setOnCheckedChangeListener { _, checked ->
                choice.automaticallyActivated = checked
            }
            buyLimitField.onTextChanged {
                choice.buyLimit = if (it.isNotBlank()) it.toInt() else null
            }
            costsList.let { list ->
                costsListManager = CostsListManager(
                        context,
                        list,
                        adventureFormModel.idManager,
                        choice::costs
                ) {
                    //TODO
                }
            }
            subNodesList.let { list ->
                adventureNodesListManager = AdventureNodesListManager(
                        context,
                        list,
                        adventureFormModel.idManager,
                        choice::subNodes
                ) {
                    backstack.goTo(AdventureNodeFormKey(it.id!!))
                }
            }
            isHiddenSwitchField.setOnCheckedChangeListener { _, checked ->
                choice.hide = checked
            }
            requirementsList.let { list ->
                //todo
            }
        }
    }

    override fun onResume() {
        super.onResume()
        with(binding) {
            nameField.setText(choice.name)
            descriptionField.setText(choice.description)
            (choice.automaticallyActivated?:false).let {
                automaticallyActivatedSwitchField.isChecked = it
            }
            choice.buyLimit?.let {
                buyLimitField.setText(it.toString())
            }

            choice.costs?.let { list ->
                costsListManager.set(list)
            }

            choice.subNodes?.let { list ->
                adventureNodesListManager.set(list)
            }

            (choice.hide?:false).let {
                isHiddenSwitchField.isChecked = it
            }

            choice.requirements?.let { list ->
                //requirementsListManager.set(list)
                //todo
            }
        }
    }
}