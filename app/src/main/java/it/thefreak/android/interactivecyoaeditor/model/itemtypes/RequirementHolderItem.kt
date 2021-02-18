package it.thefreak.android.interactivecyoaeditor.model.itemtypes

import it.thefreak.android.interactivecyoaeditor.model.entities.Requirement

interface RequirementHolderItem: Item {
    var hide: Boolean?
    var requirements: ArrayList<Requirement>?
}