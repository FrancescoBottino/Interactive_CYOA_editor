package it.thefreak.android.interactivecyoaeditor.model

import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

val module = SerializersModule {
    polymorphic(Requirement::class) {
        subclass(ChoiceSelectionRequirement::class)
        subclass(PointAmountRequirement::class)
        subclass(PointComparisonRequirement::class)
    }
    polymorphic(CostModifier::class) {
        subclass(AdditiveCostModifier::class)
        subclass(MultiplicativeCostModifier::class)
    }
}

val format = Json { serializersModule = module }