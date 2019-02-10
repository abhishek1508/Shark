package com.android.sharkteeth.di

import android.app.Application
import com.android.sharkteeth.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Provides
import dagger.Module
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApiModule {

    companion object {
        const val BASE_URL: String = "base_url"
    }

    @Provides
    @Named(BASE_URL)
    fun provideBaseUrl(): String {
        return BuildConfig.HOST
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideHttpCache(context: Application): Cache? {
        val cacheSize: Long = 50 * 1024 * 1024 //10 MiB cache size
        return Cache(context.cacheDir, cacheSize)
    }

    @Provides
    @Singleton
    internal fun provideCacheOverrideInterceptor(): Interceptor {
        return Interceptor { chain ->
            val maxStale = 60 * 60 * 24 * 7 // tolerate 1-week stale
            chain.proceed(chain.request())
                    .newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-age=$maxStale")
                    .build()
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor, cache: Cache?,
                            cacheOverrideInterceptor: Interceptor): OkHttpClient {
        val client = OkHttpClient.Builder()
        return client
                .addInterceptor(loggingInterceptor)
                .cache(cache)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(@Named(BASE_URL) baseUrl: String, gson: Gson, client: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(client)
    }
}