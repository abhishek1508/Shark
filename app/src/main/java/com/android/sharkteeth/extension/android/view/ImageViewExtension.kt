package com.android.sharkteeth.extension.android.view

import android.widget.ImageView
import com.android.sharkteeth.di.GlideApp
import com.bumptech.glide.request.RequestOptions


fun ImageView.loadImage(url: String?) {
    val reqOptions = RequestOptions().centerCrop()
    GlideApp.with(context)
            .load(url)
            .apply(reqOptions)
            .into(this)
}

fun ImageView.loadCircularImage(url: String?) {
    val reqOptions = RequestOptions().circleCrop()
    GlideApp.with(context)
            .load(url)
            .apply(reqOptions)
            .into(this)
}

