package it.thefreak.android.interactivecyoaeditor.model.itemtypes

import it.thefreak.android.interactivecyoaeditor.model.entities.Style

interface StylableItem: Item {
    var style: Style?
}