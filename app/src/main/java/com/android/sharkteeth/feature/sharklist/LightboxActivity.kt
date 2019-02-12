package com.android.sharkteeth.feature.sharklist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.android.sharkteeth.R
import com.android.sharkteeth.base.BaseActivity
import com.android.sharkteeth.di.AppComponent
import com.android.sharkteeth.extension.android.view.loadImage
import com.android.sharkteeth.feature.api.entity.Photo
import com.android.sharkteeth.feature.api.entity.PhotoInfo
import kotlinx.android.synthetic.main.activity_lightbox.*

const val INTENT_EXTRA_PHOTO = "intent.extra.photo"
fun Context.getLightboxIntent(photo: Photo): Intent {
    return Intent(this, LightboxActivity::class.java).apply {
        putExtra(INTENT_EXTRA_PHOTO, photo)
    }
}

class LightboxActivity: BaseActivity<LightboxContract.LightboxPresenter, LightboxContract.LightboxView>(),
        LightboxContract.LightboxView  {

    // BaseActivity ////////////////////////////////////////////////////////////////////////////////
    override fun getView(): LightboxContract.LightboxView? = this

    override fun getLayout(): Int = R.layout.activity_lightbox

    override fun initDagger(appComponent: AppComponent) {
        appComponent.lightboxBuilder().lightboxActivity(this).build().inject(this)
    }

    override fun initView() {

    }

    // Appcompat activity //////////////////////////////////////////////////////////////////////////
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val photo = intent.getParcelableExtra<Photo>(INTENT_EXTRA_PHOTO)
        presenter.getPhotoInfo(photo.id)
    }

    // LightboxContract.LightboxView methods ///////////////////////////////////////////////////////
    override fun showPhotoInfo(photoInfo: PhotoInfo) {
        photoDetail.loadImage(getString(R.string.original_image_url, photoInfo.photo.farm,
                                        photoInfo.photo.server, photoInfo.photo.id,
                                        photoInfo.photo.secret),
                              getString(R.string.image_url, photoInfo.photo.farm,
                                        photoInfo.photo.server, photoInfo.photo.id,
                                        photoInfo.photo.secret))
    }
}