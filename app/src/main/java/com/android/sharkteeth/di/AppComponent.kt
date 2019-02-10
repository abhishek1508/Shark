package com.android.sharkteeth.di

import android.app.Application
import com.android.sharkteeth.feature.di.SharkListComponent
import com.google.gson.Gson
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ApiModule::class, AndroidSupportInjectionModule::class])
interface AppComponent: AndroidInjector<DaggerApplication> {

    fun provideGson(): Gson

    fun retrofitBuilder(): Retrofit.Builder

    fun okHttpClient(): OkHttpClient

    fun sharkListBuilder(): SharkListComponent.Builder

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun application(application: Application): Builder
    }
}