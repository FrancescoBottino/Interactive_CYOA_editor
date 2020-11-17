package it.thefreak.android.interactivecyoaeditor.ui.editor

import androidx.annotation.DrawableRes
import androidx.annotation.MenuRes
import androidx.annotation.StringRes
import com.zhuinden.simplestackextensions.fragments.DefaultFragmentKey

abstract class FragmentKey: DefaultFragmentKey() {

    //TODO define how menu behaves after new fragment is attached.
    // only change existing features

    // TODO title res to nullable -> when null, dont change
    // TODO menu res when null, dont change

    // todo create interface for fragment to extend that receives menu clicks and navigation icon clicks

    //TODO set which icon for navigation. (if null, no navigation icon)

    /*
    @get:StringRes
    abstract val titleRes: Int
    @get:DrawableRes
    abstract val backNavigationIcon: Int?
    @get:MenuRes
    abstract val menuRes: Int?

     */
}