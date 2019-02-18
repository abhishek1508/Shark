package com.android.sharkteeth.feature.sharklist

import android.os.Environment
import android.util.Log
import com.android.sharkteeth.base.BaseView
import com.android.sharkteeth.factory.CallbackWrapper
import com.android.sharkteeth.feature.api.entity.PhotoInfo
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Callable
import javax.inject.Inject

const val FILE_EXT = ".jpg"
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
        if (disposable != null && !disposable.isDisposed) {
            disposable.dispose()
        }
    }

    //LightboxContract.LightboxPresenter methods ///////////////////////////////////////////////////
    override fun requestPermissions(rxPermissions: RxPermissions) {
        rxPermissions
                .request(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe {
                    if (it) { view?.onPermissionGranted() }
                    else { view?.onPermissionNotGranted() }
                }
    }

    override fun getPhotoInfo(photoId: String?) {
        val map = hashMapOf<String, String>()
        map["method"] = "flickr.photos.getInfo"
        map["format"] = "json"
        map["nojsoncallback"] = "1"
        map["photo_id"] = photoId!!
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

    override fun downloadToPhone(downloadURL: String) {
        view?.onDownloadStarted()
        var fileOutputStream: FileOutputStream? = null
        var inputStream: InputStream? = null
        Observable.fromCallable(Callable<Boolean> {
            try {
                val url = URL(downloadURL)
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "GET"
                conn.doOutput = true
                conn.connect()

                val sdcardRoot = Environment.getExternalStorageDirectory()
                val file = File(sdcardRoot, "IMG_${System.currentTimeMillis()}"+ FILE_EXT)
                fileOutputStream = FileOutputStream(file)
                inputStream = conn.inputStream
                var downloadedSize = 0
                val buffer = ByteArray(1024)
                var bufferLength = 0
                while({bufferLength = inputStream?.read(buffer)!!; bufferLength}() > 0) {
                    fileOutputStream?.write(buffer, 0, bufferLength)
                    downloadedSize += bufferLength
                }
            } catch(e: IOException) {
                Log.d("TAG", "exception: ${e.message}")
                return@Callable false
            } finally {
                fileOutputStream?.close()
                inputStream?.close()
            }
            true
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {value -> view?.onDownloadStatus(value) }

    }
}