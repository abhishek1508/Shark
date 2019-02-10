package com.android.sharkteeth.feature.di

import com.android.sharkteeth.feature.sharklist.SharkListContract
import com.android.sharkteeth.feature.sharklist.SharkListPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class SharkListModule {

    @Binds
    abstract fun provideSharkListPresenter(sharkListPresenter: SharkListPresenter):
            SharkListContract.SharkListPresenter
}