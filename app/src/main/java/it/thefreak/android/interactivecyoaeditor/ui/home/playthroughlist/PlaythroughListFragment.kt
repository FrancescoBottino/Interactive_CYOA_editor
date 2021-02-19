package it.thefreak.android.interactivecyoaeditor.ui.home.playthroughlist

import android.os.Bundle
import android.view.View
import com.zhuinden.simplestackextensions.fragments.KeyedFragment
import it.thefreak.android.interactivecyoaeditor.R
import it.thefreak.android.interactivecyoaeditor.databinding.PlaythroughListFragmentBinding

class PlaythroughListFragment: KeyedFragment(R.layout.playthrough_list_fragment) {
    private lateinit var binding: PlaythroughListFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = PlaythroughListFragmentBinding.bind(view)
        with(binding) {
            //todo
        }
    }
}