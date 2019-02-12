package com.android.sharkteeth.feature.sharklist

import com.android.sharkteeth.base.BasePresenter
import com.android.sharkteeth.base.BaseView
import com.android.sharkteeth.feature.api.entity.PhotoInfo

interface LightboxContract {
    interface LightboxView: BaseView {
        fun showPhotoInfo(photoInfo: PhotoInfo)
    }

    interface LightboxPresenter: BasePresenter<LightboxView> {
        fun getPhotoInfo(photoId: String)
    }
}