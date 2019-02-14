package com.android.sharkteeth.feature.sharklist

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.sharkteeth.R
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.layout_fragment_lightbox.view.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Callable


class LightboxContentFragment: Fragment() {

    private lateinit var callback: TaskCallback
    private lateinit var  rxPermissions: RxPermissions
    private var url: String? = ""

    companion object {
        const val URL_KEY = "url.key"
        fun newInstance(url: String): LightboxContentFragment {
            val bundle = Bundle()
            bundle.putString(URL_KEY, url)
            val fragment = LightboxContentFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        rxPermissions = RxPermissions(this)
        if (arguments != null) {
            url = arguments?.getString(URL_KEY)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callback = context as TaskCallback
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.layout_fragment_lightbox, container, false)
        view.download.setOnClickListener {
            rxPermissions
                    .request(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe {
                        if (it) { downloadToStorage() }
                        else {
                            Toast.makeText(activity, activity?.resources?.getString(R.string.no_permission),
                                           Toast.LENGTH_LONG).show()
                        }
                    }
        }
        return view
    }

    private fun downloadToStorage() {
        callback.onDownloadStarted()
        var fileOutputStream: FileOutputStream? = null
        var inputStream: InputStream? = null
        Observable.fromCallable(object : Callable<Boolean> {
            override fun call(): Boolean {
                try {
                    val image = url
                    val url = URL(image)
                    val conn = url.openConnection() as HttpURLConnection
                    conn.requestMethod = "GET"
                    conn.doOutput = true
                    conn.connect()

                    val sdcardRoot = Environment.getExternalStorageDirectory()
                    val file = File(sdcardRoot, "somefile.jpg")
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
                    return false
                } finally {
                    fileOutputStream?.close()
                    inputStream?.close()
                }
                return true
            }

        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {value ->
                    callback.onDownloadComplete(value)
                }
    }

    interface TaskCallback {
        fun onDownloadStarted()

        fun onDownloadComplete(isSuccess: Boolean)
    }
}