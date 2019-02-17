package com.android.sharkteeth.feature.di

import com.android.sharkteeth.di.ActivityScope
import com.android.sharkteeth.feature.sharklist.LightboxActivity
import com.android.sharkteeth.feature.sharklist.LightboxContentFragment
import dagger.BindsInstance
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [LightboxModule::class])
interface LightboxComponent {
    fun inject(fragment: LightboxContentFragment)

    @Subcomponent.Builder
    interface Builder {
        fun build(): LightboxComponent

        @BindsInstance
        fun lightboxFragment(fragment: LightboxContentFragment): Builder
    }
}