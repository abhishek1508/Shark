package com.android.sharkteeth.feature.sharklist

import android.util.Log
import com.android.sharkteeth.base.BaseView
import com.android.sharkteeth.factory.CallbackWrapper
import com.android.sharkteeth.feature.api.entity.Photos
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SharkListPresenter @Inject constructor(): SharkListContract.SharkListPresenter {

    var view: SharkListContract.SharkListView? = null
    private lateinit var disposable: Disposable
    @Inject lateinit var repository: Repository

    // BasePresenter methods ///////////////////////////////////////////////////////////////////////
    override fun onViewAttached(view: SharkListContract.SharkListView?) {
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

    // SharkListContract.SharkListPresenter methods ////////////////////////////////////////////////

    override fun getImages(pageNumber: Int, loadMore: Boolean) {
        val map = hashMapOf<String, String>()
        map["method"] = "flickr.photos.search"
        map["text"] = "shark"
        map["format"] = "json"
        map["nojsoncallback"] = "1"
        map["page"] = pageNumber.toString()
        map["extras"] = "url_t.url_c.url_l.url_o"
        disposable = repository.getImages(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : CallbackWrapper<Photos>(view as BaseView) {
                    override fun onResponseError(message: String) {
                        Log.d("Trsting", message)
                    }

                    override fun onResponse401() {
                        Log.d("Trsting", "401")
                    }

                    override fun onResponseSuccess(response: Photos?) {
                        Log.d("Trsting", "response: $response")
                        view?.nextPage(response?.photos?.page!!, response.photos.pages)
                        view?.showImages(response?.photos?.photo!!, loadMore)
                    }
                })
    }
}