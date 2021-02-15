package it.thefreak.android.interactivecyoaeditor.ui.editor.components

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.annotation.StringRes
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import it.thefreak.android.interactivecyoaeditor.R
import it.thefreak.android.interactivecyoaeditor.databinding.ListItemSelectionBinding
import it.thefreak.android.interactivecyoaeditor.model.IdManager
import it.thefreak.android.interactivecyoaeditor.model.IdentifiableItem
import it.thefreak.android.interactivecyoaeditor.onClick
import kotlin.reflect.KClass

class Chooser<T: IdentifiableItem>(
    private val ctx: Context,
    private val type: KClass<T>,
    @StringRes private val name: Int,
    private val idManager: IdManager,
    private val viewHolderFactory:(View, ListItemSelectionBinding, T) -> Unit
) {
    fun choose(currentlySelected: Set<String>? = null, onChoose: (T)->Unit) {
        val items = idManager.idMap.values
            .filter { it::class == type && !(currentlySelected?.contains(it.id!!)?:false) } as List<T>

        val adapter = object: BaseAdapter() {
            var listener: ((T)->Unit)? = null

            override fun getCount(): Int {
                return items.size
            }

            override fun getItem(position: Int): Any {
                return items[position]
            }

            override fun getItemId(position: Int): Long {
                return items[position].hashCode().toLong()
            }

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = convertView ?: LayoutInflater.from(ctx).inflate(R.layout.list_item_selection, parent, false)
                val binding = ListItemSelectionBinding.bind(view)
                val item = items[position]

                viewHolderFactory(view, binding, item)

                binding.content.onClick {
                    listener?.invoke(item)
                }

                return view
            }
        }

        val dialog = MaterialAlertDialogBuilder(ctx)
            .setTitle(name)
            .setCancelable(true)
            .setAdapter(adapter, null)
            .create()

        adapter.listener = { item ->
            dialog.dismiss()
            onChoose(item)
        }

        dialog.show()
    }
}