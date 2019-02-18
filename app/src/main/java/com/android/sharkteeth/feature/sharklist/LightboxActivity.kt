package com.android.sharkteeth.feature.sharklist

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewCompat
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.widget.ImageView
import com.android.sharkteeth.R
import com.android.sharkteeth.extension.android.support.v7.app.addFragmentSafely
import com.android.sharkteeth.extension.android.view.loadImage
import com.android.sharkteeth.feature.api.entity.Photo
import kotlinx.android.synthetic.main.activity_lightbox.*
import kotlinx.android.synthetic.main.layout_toolbar.*

const val INTENT_EXTRA_PHOTO = "intent.extra.photo"
const val INTENT_EXTRA_TRANSITION_ANIMATION = "intent.extra.transition.animation"
fun Context.getLightboxIntent(photo: Photo, transitionName: String?): Intent {
    return Intent(this, LightboxActivity::class.java).apply {
        putExtra(INTENT_EXTRA_PHOTO, photo)
        putExtra(INTENT_EXTRA_TRANSITION_ANIMATION, transitionName)
    }
}

class LightboxActivity: AppCompatActivity()  {

    private var scaleFactor: Float = 1f
    private lateinit var scaleGestureDetector: ScaleGestureDetector

    // Appcompat activity //////////////////////////////////////////////////////////////////////////
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lightbox)
        animateView()
        initView()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        scaleGestureDetector.onTouchEvent(event)
        return true
    }

    // Private methods /////////////////////////////////////////////////////////////////////////////
    private fun animateView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val transitionName = intent.getStringExtra(INTENT_EXTRA_TRANSITION_ANIMATION)
            photoDetail.transitionName = transitionName
        }
    }

    private fun initView() {
        val photo = intent.getParcelableExtra<Photo>(INTENT_EXTRA_PHOTO)
        scaleGestureDetector = ScaleGestureDetector(this, ScaleListener())
        photoDetail.loadImage(getString(R.string.original_image_url, photo?.farm, photo?.server, photo?.id, photo?.secret))
        this.addFragmentSafely(LightboxContentFragment.newInstance(photo), getString(R.string.fragment_tag), R.id.contentView)
    }

    // ScaleGestureDetector ////////////////////////////////////////////////////////////////////////
    inner class ScaleListener: ScaleGestureDetector.SimpleOnScaleGestureListener() {

        override fun onScale(detector: ScaleGestureDetector?): Boolean {
            scaleFactor *= detector?.scaleFactor!!
            scaleFactor = Math.max(MIN_ZOOM, Math.min(scaleFactor, MAX_ZOOM))
            photoDetail.scaleX = scaleFactor
            photoDetail.scaleY = scaleFactor
            return true
        }
    }
}