package com.android.sharkteeth.factory

import com.android.sharkteeth.base.BaseView
import io.reactivex.observers.DisposableObserver
import retrofit2.adapter.rxjava2.Result
import java.lang.ref.WeakReference

abstract class CallbackWrapper<T> constructor(weakReferenceView: BaseView): DisposableObserver<Result<T>>() {

    private val weakReference = WeakReference(weakReferenceView)

    override fun onError(e: Throwable) {
        onResponseError(e.message!!)
    }

    override fun onComplete() {

    }

    override fun onNext(t: Result<T>) {
        when(t.response()?.code()) {
            200 -> onResponseSuccess(t.response()?.body())
            401 -> onResponse401()
            else -> onResponseError(t.error().toString())
        }
    }

    abstract fun onResponseError(message: String)

    abstract fun onResponse401()

    abstract fun onResponseSuccess(response: T?)
}