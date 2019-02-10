package com.android.sharkteeth.base

interface BasePresenter<in V: BaseView> {
    fun onViewAttached(view: V?)

    fun onViewDetached()

    fun clearResources()
}