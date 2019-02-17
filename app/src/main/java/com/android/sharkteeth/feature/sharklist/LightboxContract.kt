package com.android.sharkteeth.feature.sharklist

import com.android.sharkteeth.base.BasePresenter
import com.android.sharkteeth.base.BaseView
import com.android.sharkteeth.feature.api.entity.PhotoInfo
import com.tbruyelle.rxpermissions2.RxPermissions

interface LightboxContract {
    interface LightboxView: BaseView {
        fun showPhotoInfo(photoInfo: PhotoInfo)

        fun onPermissionGranted()

        fun onPermissionNotGranted()

        fun onDownloadStarted()

        fun onDownloadStatus(isSuccess: Boolean)
    }

    interface LightboxPresenter: BasePresenter<LightboxView> {
        fun getPhotoInfo(photoId: String?)

        fun requestPermissions(rxPermissions: RxPermissions)

        fun downloadToPhone(downloadURL: String)
    }
}