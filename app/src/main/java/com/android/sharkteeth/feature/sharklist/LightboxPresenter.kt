package com.android.sharkteeth.feature.sharklist

import com.android.sharkteeth.base.BaseView
import com.android.sharkteeth.factory.CallbackWrapper
import com.android.sharkteeth.feature.api.entity.PhotoInfo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LightboxPresenter @Inject constructor(): LightboxContract.LightboxPresenter {

    var view: LightboxContract.LightboxView? = null
    private lateinit var disposable: Disposable
    @Inject lateinit var repository: Repository

    // BasePresenter methods ///////////////////////////////////////////////////////////////////////
    override fun onViewAttached(view: LightboxContract.LightboxView?) {
        this.view = view
    }

    override fun onViewDetached() {
        clearResources()
        this.view = null
    }

    override fun clearResources() {
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
    }

    //LightboxContract.LightboxPresenter methods ///////////////////////////////////////////////////
    override fun getPhotoInfo(photoId: String) {
        val map = hashMapOf<String, String>()
        map["method"] = "flickr.photos.getInfo"
        map["format"] = "json"
        map["nojsoncallback"] = "1"
        map["photo_id"] = photoId
        disposable = repository.getImageInfo(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : CallbackWrapper<PhotoInfo>(view as BaseView) {
                    override fun onResponseSuccess(response: PhotoInfo?) {
                        view?.showPhotoInfo(response!!)
                    }

                    override fun onResponse401() {

                    }

                    override fun onResponseError(message: String) {

                    }
                })
    }
}