package com.android.sharkteeth.factory

import okhttp3.HttpUrl
import okhttp3.Request
import okhttp3.Response
import okhttp3.Interceptor

class APIInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response? {
        val original: Request? = chain?.request()
        val originalHttpUrl: HttpUrl? = original?.url()

        val urlBuilder: HttpUrl.Builder? = originalHttpUrl?.newBuilder()
        urlBuilder?.addQueryParameter("api_key", "99685be3c9f3976e4e2db1763ca62022")
        val url: HttpUrl? = urlBuilder?.build()

        val requestBuilder: Request.Builder? = original?.newBuilder()?.url(url!!)

        val request: Request? = requestBuilder?.build()
        return chain?.proceed(request!!)
    }
}