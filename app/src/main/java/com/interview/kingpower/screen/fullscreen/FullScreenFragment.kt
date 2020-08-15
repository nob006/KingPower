package com.interview.kingpower.screen.fullscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.interview.kingpower.databinding.FragmentFullScreenBinding
import com.interview.kingpower.model.PhotoResItem
import com.interview.kingpower.viewmodel.ToolbarViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FullScreenFragment : Fragment() {

    private val toolbarViewModel by sharedViewModel<ToolbarViewModel>()

    companion object {
        private const val KEY_PHOTO_RES_ITEM = "PhotoResItem"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = FragmentFullScreenBinding.inflate(inflater, container, false)
        arguments?.getParcelable<PhotoResItem>(KEY_PHOTO_RES_ITEM)?.let { photoResItem ->
            view.photoResItem = photoResItem
            toolbarViewModel.titleText.value = photoResItem.title
        }
        return view.root
    }
}