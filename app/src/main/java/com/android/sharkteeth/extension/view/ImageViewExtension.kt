package com.android.sharkteeth.extension.android.view

import android.support.annotation.Nullable
import android.widget.ImageView
import com.android.sharkteeth.di.GlideApp
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadImage(url: String?) {
    val reqOptions = RequestOptions().centerCrop()
    GlideApp.with(context)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .thumbnail(0.1f)
            .apply(reqOptions)
            .into(this)
}

fun ImageView.loadCircularImage(url: String?) {
    val reqOptions = RequestOptions().circleCrop()
    GlideApp.with(context)
            .load(url)
            .thumbnail(0.1f)
            .apply(reqOptions)
            .into(this)
}

