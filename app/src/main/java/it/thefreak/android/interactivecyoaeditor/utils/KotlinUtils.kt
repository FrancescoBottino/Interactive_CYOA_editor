package it.thefreak.android.interactivecyoaeditor

inline fun <A, B, R> ifBothNotNull(a: A?, b: B?, code: ((A, B) -> R)): R? {
    return if (a != null && b != null) {
        code(a, b)
    } else null
}

inline fun <A, B, R> ifBothNotNull(a: A?, b: B?, default: R, code: ((A, B) -> R)): R? {
    return if (a != null && b != null) {
        code(a, b)
    } else default
}