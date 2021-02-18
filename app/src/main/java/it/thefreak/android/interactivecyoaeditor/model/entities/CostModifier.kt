package it.thefreak.android.interactivecyoaeditor.model.entities

import it.thefreak.android.interactivecyoaeditor.model.itemtypes.IdManageableItem
import it.thefreak.android.interactivecyoaeditor.model.itemtypes.PolymorphicListManageableItem
import it.thefreak.android.interactivecyoaeditor.model.itemtypes.RequirementHolderItem

interface CostModifier: IdManageableItem, PolymorphicListManageableItem<CostModifierType>,
    RequirementHolderItem