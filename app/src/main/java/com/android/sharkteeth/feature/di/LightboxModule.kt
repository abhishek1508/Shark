package com.android.sharkteeth.feature.di

import com.android.sharkteeth.feature.sharklist.LightboxContract
import com.android.sharkteeth.feature.sharklist.LightboxPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class LightboxModule {

    @Binds
    abstract fun provideLightboxPresenter(lightboxPresenter: LightboxPresenter):
            LightboxContract.LightboxPresenter
}