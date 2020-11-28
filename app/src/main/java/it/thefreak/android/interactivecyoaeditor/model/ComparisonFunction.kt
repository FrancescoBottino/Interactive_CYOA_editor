package it.thefreak.android.interactivecyoaeditor.model

enum class ComparisonFunction(
        val symbol: String
) {
    MORE_THAN(">"),
    MORE_EQUAL_THAN("≥"),
    EQUAL("="),
    LESS_THAN("<"),
    LESS_EQUAL_THAN("≤")
}