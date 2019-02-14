package com.android.sharkteeth.feature.sharklist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import com.android.sharkteeth.R
import com.android.sharkteeth.base.BaseActivity
import com.android.sharkteeth.di.AppComponent
import com.android.sharkteeth.extension.android.support.v7.app.addFragmentSafely
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
        LightboxContract.LightboxView, LightboxContentFragment.TaskCallback  {

    // BaseActivity ////////////////////////////////////////////////////////////////////////////////
    override fun getView(): LightboxContract.LightboxView? = this

    override fun getLayout(): Int = R.layout.activity_lightbox

    override fun initDagger(appComponent: AppComponent) {
        appComponent.lightboxBuilder().lightboxActivity(this).build().inject(this)
    }

    override fun initView() {
        if (intent != null) {
            val photo = intent.getParcelableExtra<Photo>(INTENT_EXTRA_PHOTO)
            photoDetail.loadImage(getString(R.string.original_image_url, photo.farm,
                                            photo.server, photo.id, photo.secret),
                                  getString(R.string.image_url, photo.farm,
                                            photo.server, photo.id, photo.secret))
            this.addFragmentSafely(LightboxContentFragment
                                           .newInstance(getString(R.string.original_image_url, photo.farm, photo.server, photo.id, photo.secret)),
                                   getString(R.string.fragment_tag), R.id.contentView)
        }


    }

    // Appcompat activity //////////////////////////////////////////////////////////////////////////
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val photo = intent.getParcelableExtra<Photo>(INTENT_EXTRA_PHOTO)
        presenter.getPhotoInfo(photo.id)
    }

    // LightboxContract.LightboxView methods ///////////////////////////////////////////////////////
    override fun showPhotoInfo(photoInfo: PhotoInfo) {

    }

    override fun onDownloadStarted() {
        downloadStatus.visibility = VISIBLE
    }

    override fun onDownloadComplete(isSuccess: Boolean) {
        downloadStatus.visibility = GONE
        if (!isSuccess) {
            Toast.makeText(this, getString(R.string.download_unsuccessful), Toast.LENGTH_LONG).show()
        }
    }
}