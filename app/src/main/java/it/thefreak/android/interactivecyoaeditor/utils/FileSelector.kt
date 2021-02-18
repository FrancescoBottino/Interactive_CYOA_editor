package it.thefreak.android.interactivecyoaeditor.utils

import android.content.Context
import java.io.File

object FileSelector {
    fun getTopFile(ctx: Context, fileName: String): File {
        return File(ctx.filesDir, fileName)
    }
    fun getFile(parent: File, fileName: String): File {
        return File(parent, fileName)
    }
}