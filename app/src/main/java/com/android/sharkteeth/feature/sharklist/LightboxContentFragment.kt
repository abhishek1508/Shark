package com.android.sharkteeth.feature.sharklist

import android.os.Bundle
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import com.android.sharkteeth.R
import com.android.sharkteeth.base.BaseFragment
import com.android.sharkteeth.di.AppComponent
import com.android.sharkteeth.extension.android.view.loadImage
import com.android.sharkteeth.feature.api.entity.Photo
import com.android.sharkteeth.feature.api.entity.PhotoInfo
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.layout_fragment_lightbox.*

const val MAX_ZOOM = 10f
const val MIN_ZOOM = 1f

class LightboxContentFragment: BaseFragment<LightboxContract.LightboxPresenter, LightboxContract
.LightboxView>(), LightboxContract.LightboxView {

    private var photo: Photo? = null
    private var scaleFactor: Float = 1f
    private lateinit var  rxPermissions: RxPermissions
    private lateinit var scaleGestureDetector: ScaleGestureDetector

    companion object {
        const val PHOTO_DETAIL = "photo.detail"
        fun newInstance(photoDetail: Photo): LightboxContentFragment {
            val bundle = Bundle()
            bundle.putParcelable(PHOTO_DETAIL, photoDetail)
            val fragment = LightboxContentFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    // BaseFragment methods ////////////////////////////////////////////////////////////////////////

    override fun getLayout(): Int = R.layout.layout_fragment_lightbox

    override fun getFragmentView(): LightboxContract.LightboxView? = this

    override fun initDagger(appComponent: AppComponent) {
        appComponent.lightboxBuilder().lightboxFragment(this).build().inject(this)
    }

    // Fragment Lifecycle methods //////////////////////////////////////////////////////////////////
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        rxPermissions = RxPermissions(this)
        scaleGestureDetector = ScaleGestureDetector(activity, ScaleListener())
        if (arguments != null) {
            photo = arguments?.getParcelable(PHOTO_DETAIL)!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(getLayout(), container, false)
        view.setOnTouchListener { _, motionEvent ->
            scaleGestureDetector.onTouchEvent(motionEvent)
            true
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.getPhotoInfo(photo?.id)
        photoDetail.loadImage(getString(R.string.image_url, photo?.farm, photo?.server, photo?.id, photo?.secret), null)
        download.setOnClickListener { presenter.requestPermissions(rxPermissions) }
    }

    // LightboxContract.LightboxView methods ///////////////////////////////////////////////////////
    override fun showPhotoInfo(photoInfo: PhotoInfo) {
        description.text = photoInfo.photo.description.content
    }

    override fun onPermissionGranted() {
        presenter.downloadToPhone(getString(R.string.original_image_url, photo?.farm, photo?.server, photo?.id, photo?.secret))
    }

    override fun onPermissionNotGranted() {
        Toast.makeText(activity, activity?.resources?.getString(R.string.no_permission), Toast.LENGTH_LONG).show()
    }

    override fun onDownloadStarted() {
        downloadStatus.visibility = VISIBLE
    }

    override fun onDownloadStatus(isSuccess: Boolean) {
        downloadStatus.visibility = GONE
        if (!isSuccess) {
            Toast.makeText(activity, getString(R.string.download_unsuccessful), Toast.LENGTH_LONG).show()
        }
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