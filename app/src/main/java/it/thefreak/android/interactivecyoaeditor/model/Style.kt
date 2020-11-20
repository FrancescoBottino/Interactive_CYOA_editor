package it.thefreak.android.interactivecyoaeditor.model

import kotlinx.serialization.Serializable

@Serializable
data class Style(
    //TODO OTHER OPTIONS
    var bgColorCode: String? = null,
    var textColorCode: String? = null,
    //var font: Font? = null
): Item