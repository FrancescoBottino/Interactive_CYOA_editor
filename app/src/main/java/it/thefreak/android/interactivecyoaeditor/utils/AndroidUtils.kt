package it.thefreak.android.interactivecyoaeditor

import android.app.Activity
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.items.AbstractItem
import com.zhuinden.simplestackextensions.fragments.DefaultFragmentKey

fun View.onClick(clickListener: ((View) -> Unit)?) {
    setOnClickListener(clickListener)
}

class Wrapper<T>(val item: T, val stringifier: (T)->String) {
    override fun toString() = stringifier(item)
}
fun <T> List<T>.getAdapterWrapper(stringifier: (T)->String) : List<Wrapper<T>> {
    return this.map{ Wrapper(it, stringifier) }
}

inline fun EditText.onTextChanged(crossinline textChangeListener: (String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable) {
            textChangeListener(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    })
}

inline fun <reified T : Activity> Activity.startActivity() {
    startActivity(Intent(this, T::class.java))
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

inline fun <T : View> T.showIf(condition: (T) -> Boolean): T {
    if (condition(this)) {
        show()
    } else {
        hide()
    }

    return this
}

inline fun <T : View> T.hideIf(condition: (T) -> Boolean): T {
    if (condition(this)) {
        hide()
    } else {
        show()
    }

    return this
}

abstract class GenericBinder<T> constructor(
        val content: T
) : AbstractItem<RecyclerView.ViewHolder>()

abstract class FragmentKey: DefaultFragmentKey() {
    //TOdo either define generic fragment key properties or remove class
}