package com.android.sharkteeth.factory

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Inject

class ServiceGeneratorFactory @Inject constructor(val retrofitBuilder: Retrofit.Builder,
                                                  val okhttpClient: OkHttpClient){

    fun <S> createService(serviceClass: Class<S>): S {
        val interceptor = APIInterceptor()
        if (!okhttpClient.interceptors().contains(interceptor)) {
            retrofitBuilder.client(okhttpClient.newBuilder().addInterceptor(interceptor).build())
        }
        return retrofitBuilder.build().create(serviceClass)
    }
}