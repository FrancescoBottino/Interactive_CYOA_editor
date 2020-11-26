package it.thefreak.android.interactivecyoaeditor.model

/**
 * to be used alongside kotlinx.serialization.Transient,
 * annotates field to be deep copied as a reference to the object, and flags it to be linked on deepLinking
 */
@Target(AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class ItemLinkField()
