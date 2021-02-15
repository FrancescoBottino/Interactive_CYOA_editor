package it.thefreak.android.interactivecyoaeditor.ui.editor.components

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import it.thefreak.android.interactivecyoaeditor.R
import it.thefreak.android.interactivecyoaeditor.databinding.ListItemSelectionBinding
import it.thefreak.android.interactivecyoaeditor.model.IdManager
import it.thefreak.android.interactivecyoaeditor.model.IdentifiableItem
import kotlin.reflect.KClass

class Chooser<T: IdentifiableItem>(
    private val ctx: Context,
    private val type: KClass<T>,
    private val idManager: IdManager,
    private val viewHolderFactory:(View, ListItemSelectionBinding, T) -> Unit
) {
    fun choose(currentlySelected: Set<String>? = null, onChoose: (T)->Unit) {
        val items = idManager.idMap.values
            .filter { it::class == type && !(currentlySelected?.contains(it.id!!)?:false) } as List<T>

        val adapter = object: BaseAdapter() {
            override fun getCount(): Int {
                return items.size
            }

            override fun getItem(position: Int): Any {
                return items[position]
            }

            override fun getItemId(position: Int): Long {
                return 0L
            }

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = convertView ?: LayoutInflater.from(ctx).inflate(R.layout.list_item_selection, parent, false)
                val binding = ListItemSelectionBinding.bind(view)
                val item = items[position]

                viewHolderFactory(view, binding, item)

                return view
            }
        }

        //TODO FIX CALLBACK NOT BEING TRIGGERED?
        MaterialAlertDialogBuilder(ctx)
            .setTitle(ctx.getString(R.string.choice_selection_requirement_dialog_title))
            .setCancelable(true)
            .setAdapter(adapter) { dialog, which ->
                onChoose(items[which])
            }
            .show()
    }
}