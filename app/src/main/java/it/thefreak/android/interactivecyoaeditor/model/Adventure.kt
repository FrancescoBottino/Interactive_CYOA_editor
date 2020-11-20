package it.thefreak.android.interactivecyoaeditor.model

import kotlinx.serialization.Serializable

@Serializable
data class Adventure(
        override var name: String? = null,
        override var description: String? = null,
        override var image: String? = null,
        override var style: Style? = null,
        var versionName: String? = null,
        var versionCode: Int? = null,
        var author: String? = null,
        var initialPoints: ArrayList<PointType>? = null,
        var adventureNodesList: ArrayList<AdventureNode>? = null,
): NarrativeItem, StylableItem