package com.android.sharkteeth.feature.di

import com.android.sharkteeth.di.ActivityScope
import com.android.sharkteeth.feature.sharklist.SharkListActivity
import dagger.BindsInstance
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [SharkListModule::class])
interface SharkListComponent {
    fun inject(activity: SharkListActivity)

    @Subcomponent.Builder
    interface Builder {
        fun build(): SharkListComponent

        @BindsInstance
        fun sharkListActivity(activity: SharkListActivity): Builder
    }
}