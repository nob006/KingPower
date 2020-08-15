package com.interview.kingpower.screen.photolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.interview.kingpower.databinding.ItemPhotoListBinding
import com.interview.kingpower.model.PhotoRes
import com.interview.kingpower.model.PhotoResItem

class PhotoListAdapter(private val list: PhotoRes, val onItemClick: (PhotoResItem) -> Unit) :
    RecyclerView.Adapter<PhotoListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ItemPhotoListBinding.inflate(inflater, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class ViewHolder(private val view: ItemPhotoListBinding) :
        RecyclerView.ViewHolder(view.root) {
        fun bind(photoResItem: PhotoResItem) {
            view.photoResItem = photoResItem
            view.imgThumbnail.setOnClickListener {
                onItemClick(photoResItem)
            }
            view.executePendingBindings()
        }
    }
}