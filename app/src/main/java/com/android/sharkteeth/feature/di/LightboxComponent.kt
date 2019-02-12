package com.android.sharkteeth.feature.di

import com.android.sharkteeth.di.ActivityScope
import com.android.sharkteeth.feature.sharklist.LightboxActivity
import dagger.BindsInstance
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [LightboxModule::class])
interface LightboxComponent {
    fun inject(activity: LightboxActivity)

    @Subcomponent.Builder
    interface Builder {
        fun build(): LightboxComponent

        @BindsInstance
        fun lightboxActivity(activity: LightboxActivity): Builder
    }
}