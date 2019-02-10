package com.android.sharkteeth.factory

import android.os.Build
import com.android.sharkteeth.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

object APIModule {
    fun getBaseURL(): String {
        return BuildConfig.HOST
    }

    fun getGson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

    fun getLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    fun getOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val client = OkHttpClient.Builder()
        return client
                .addInterceptor(loggingInterceptor)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build()
    }
}