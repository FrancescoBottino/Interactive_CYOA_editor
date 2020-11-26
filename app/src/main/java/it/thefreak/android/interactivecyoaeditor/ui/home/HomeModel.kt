package it.thefreak.android.interactivecyoaeditor.ui.home

import com.zhuinden.simplestack.Backstack

class HomeModel(
        private val homeKey: HomeKey,
        private val backstack: Backstack
) {

    val repo = AdventuresRepository()
    /*
    private var adventure: Adventure? = null
    fun getAdventure(ctx: Context): Adventure {
        return if(adventure == null) {
            loadAction(ctx).apply {
                adventure = this
                deepRegisterItem(idManager)
                deepLinkItem(idManager)
            }
        } else {
            adventure!!
        }
    }
    val idManager: IdManager by lazy {
        IdManager()
    }

     */


}
