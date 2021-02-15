package it.thefreak.android.interactivecyoaeditor

import android.content.Context
import android.net.Uri
import androidx.core.net.toUri
import it.thefreak.android.interactivecyoaeditor.model.Adventure
import it.thefreak.android.interactivecyoaeditor.model.format

const val FILENAME: String = "adventure_file"

fun getFileUri(context: Context): Uri {
    return Cacher.getBeanFile(context, FILENAME).let { mockFile ->
        if(!mockFile.exists()) {
            saveAction(context, Adventure())
        }
        mockFile.toUri()
    }
}

fun saveAction(ctx: Context, adventure: Adventure): Boolean {
    Cacher.savePersistent(ctx, FILENAME, adventure, format)
    return true
}
fun loadAction(ctx: Context): Adventure {
    return (Cacher.loadPersistent<Adventure>(ctx, FILENAME, format)?:Adventure())
}