package it.thefreak.android.interactivecyoaeditor.model

import kotlinx.serialization.Serializable

@Serializable
data class Adventure(
        override var id: String? = null,
        override var ordinal: Int? = null,
        override var name: String? = null,
        override var description: String? = null,
        override var icon: String? = null,
        override var image: String? = null,
        override var style: Style? = null,
        var version: String? = null,
        var author: String? = null,
        var initialPoints: ArrayList<PointType>? = null,
        var adventureNodesList: ArrayList<AdventureNode>? = null,
        var engineVersion: String? = null,
): ListableItem, NarrativeItem, StylableItem {
    override fun deepCopy(idManager: IdManager): Adventure {
        return Adventure(
                name = this.name.copy(),
                description = this.description.copy(),
                icon = this.icon.copy(),
                image = this.image.copy(),
                style = this.style?.deepCopy(idManager),
                version = this.version.copy(),
                author = this.author.copy(),
                initialPoints = this.initialPoints.copy(idManager),
                adventureNodesList = this.adventureNodesList.copy(idManager),
                engineVersion = this.engineVersion,
        )
    }

    override fun deepRegister(idManager: IdManager) {
        this.initialPoints?.forEach {
            it.deepRegister(idManager)
        }
        this.adventureNodesList?.forEach {
            it.deepRegister(idManager)
        }
    }

    override fun deepDelete(idManager: IdManager) {
        this.initialPoints?.forEach {
            it.deepDelete(idManager)
        }
        this.adventureNodesList?.forEach {
            it.deepDelete(idManager)
        }
    }
}