package com.android.sharkteeth

import android.app.Application
import com.android.sharkteeth.di.AppComponent
import com.android.sharkteeth.di.DaggerAppComponent

class App: Application() {

    lateinit var appComponent: AppComponent
        private set

    companion object {
        lateinit var app: App
            internal set
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = initDagger(this)
    }

    private fun initDagger(app: Application): AppComponent {
       return DaggerAppComponent.builder().application(app).build()
    }
}