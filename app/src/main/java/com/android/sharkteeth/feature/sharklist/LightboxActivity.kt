package com.android.sharkteeth.feature.sharklist

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.android.sharkteeth.R
import com.android.sharkteeth.extension.android.support.v7.app.addFragmentSafely
import com.android.sharkteeth.feature.api.entity.Photo

const val INTENT_EXTRA_PHOTO = "intent.extra.photo"
fun Context.getLightboxIntent(photo: Photo): Intent {
    return Intent(this, LightboxActivity::class.java).apply {
        putExtra(INTENT_EXTRA_PHOTO, photo)
    }
}

class LightboxActivity: AppCompatActivity()  {

    // Appcompat activity //////////////////////////////////////////////////////////////////////////
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lightbox)
        initView()
    }

    // Private methods /////////////////////////////////////////////////////////////////////////////

    private fun initView() {
        val photo = intent.getParcelableExtra<Photo>(INTENT_EXTRA_PHOTO)
        this.addFragmentSafely(LightboxContentFragment.newInstance(photo), getString(R.string.fragment_tag), R.id.contentView)
    }
}