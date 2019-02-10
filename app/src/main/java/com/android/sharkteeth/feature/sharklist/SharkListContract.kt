package com.android.sharkteeth.feature.sharklist

import com.android.sharkteeth.base.BasePresenter
import com.android.sharkteeth.base.BaseView
import com.android.sharkteeth.feature.api.entity.Photo

interface SharkListContract {
    interface SharkListView: BaseView {
        fun showImages(photoList: MutableList<Photo>)

        fun errorReceived()
    }

    interface SharkListPresenter: BasePresenter<SharkListView> {
        fun getImages()
    }
}