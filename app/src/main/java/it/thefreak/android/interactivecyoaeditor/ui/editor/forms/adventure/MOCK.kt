package it.thefreak.android.interactivecyoaeditor.ui.editor.forms.adventure

import android.content.Context
import it.thefreak.android.interactivecyoaeditor.model.Adventure

const val FILENAME: String = "adventure_file"

fun saveAction(ctx: Context, adventure: Adventure): Boolean {
    //Cacher.savePersistent(ctx, FILENAME, adventure)
    return true
}
fun loadAction(ctx: Context): Adventure {
    //return (Cacher.loadPersistent<Adventure>(ctx, FILENAME)?:Adventure())
    return Adventure()
}