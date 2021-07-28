package com.incrediblehohol.userlist.utils.ui.databinding

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("imageUrl")
fun setImage(imageView: ImageView, url: String?) {
    Log.d("myTag", "$url")
    url ?: return
    Picasso.get()
        .load(url)
        .noPlaceholder()
        .fit()
        .centerCrop()
        .into(imageView)
}