package com.interview.kingpower.screen.photolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.interview.kingpower.R
import com.interview.kingpower.databinding.FragmentPhotoListBinding
import com.interview.kingpower.model.PhotoRes
import com.interview.kingpower.model.PhotoResItem
import com.interview.kingpower.view.GridSpacingItemDecoration
import com.interview.kingpower.viewmodel.PhotoViewModel
import kotlinx.android.synthetic.main.fragment_photo_list.*
import org.koin.android.ext.android.inject

class PhotoListFragment : Fragment() {

    companion object {
        private const val SPAN_COUNT = 2
        private const val SPACING = 40
    }

    private val photoViewModel: PhotoViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = DataBindingUtil.inflate<FragmentPhotoListBinding>(
            inflater,
            R.layout.fragment_photo_list,
            container,
            false
        )
        return view.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initObserve()
        photoViewModel.getPhotoList()
    }

    private fun initObserve() {
        photoViewModel.photoRes.observe(viewLifecycleOwner, Observer { photoRes ->
            initListView(photoRes)
        })
    }

    private fun initListView(photoRes: PhotoRes) {
        recycleView.layoutManager = GridLayoutManager(context, SPAN_COUNT)
        recycleView.adapter = PhotoListAdapter(photoRes, onItemClick = {
            navigateToFullScreenFragment(it)
        })
        recycleView.addItemDecoration(GridSpacingItemDecoration(SPAN_COUNT, SPACING, true))
    }

    private fun navigateToFullScreenFragment(photoResItem: PhotoResItem) {
        findNavController().navigate(PhotoListFragmentDirections.navigateFullscreen(photoResItem))
    }
}