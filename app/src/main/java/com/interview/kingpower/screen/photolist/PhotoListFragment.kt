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
import com.google.android.material.snackbar.Snackbar
import com.interview.kingpower.R
import com.interview.kingpower.databinding.FragmentPhotoListBinding
import com.interview.kingpower.view.GridSpacingItemDecoration
import com.interview.kingpower.viewmodel.PhotoViewModel
import com.interview.kingpower.viewmodel.ToolbarViewModel
import kotlinx.android.synthetic.main.fragment_photo_list.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhotoListFragment : Fragment() {

    companion object {
        private const val SPAN_COUNT = 2
        private const val SPACING = 40
    }

    private val photoViewModel by viewModel<PhotoViewModel>()
    private val toolbarViewModel by sharedViewModel<ToolbarViewModel>()

    private lateinit var adapter: PhotoListAdapter

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
        initView()
        initObserve()
    }

    private fun initView() {
        toolbarViewModel.titleText.value = getString(R.string.app_name)
        recycleView.layoutManager = GridLayoutManager(context, SPAN_COUNT)
        adapter = PhotoListAdapter(onItemClick = { photoResItem ->
            findNavController().navigate(PhotoListFragmentDirections.navigateFullscreen(photoResItem))
        })
        recycleView.adapter = adapter
        recycleView.addItemDecoration(GridSpacingItemDecoration(SPAN_COUNT, SPACING, true))
    }

    private fun initObserve() {
        photoViewModel.photoRes.observe(viewLifecycleOwner, Observer { photoRes ->
            adapter.setData(photoRes)
        })

        photoViewModel.showMessage.observe(viewLifecycleOwner, Observer { messageId ->
            Snackbar.make(recycleView, getText(messageId), Snackbar.LENGTH_SHORT).show()
        })
    }
}
