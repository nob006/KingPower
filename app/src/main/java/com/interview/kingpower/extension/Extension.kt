package com.interview.kingpower.extension

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.interview.kingpower.R


@BindingAdapter("imageUrl")
fun setImageUrl(view: ImageView, url: String?) {
    url?.let {
        val urlBuilder = GlideUrl(
            "$url.PNG", LazyHeaders.Builder()
                .addHeader("User-Agent", view.context.resources.getString(R.string.user_agent))
                .build()
        )

        Glide.with(view.context)
            .load(urlBuilder)
            .into(view)
    }
}