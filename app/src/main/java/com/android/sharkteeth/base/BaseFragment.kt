package com.android.sharkteeth.base

import android.content.Context
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import com.android.sharkteeth.App
import com.android.sharkteeth.di.AppComponent
import javax.inject.Inject

abstract class BaseFragment <P: BasePresenter<V>, V: BaseView>: Fragment(), BaseView {
    @Inject
    lateinit var presenter: P
        internal set

    private var fragmentContext: Context? = null

    override fun onAttach(context: Context?) {
        fragmentContext = context
        initDagger((activity?.application as App).appComponent)
        presenter.onViewAttached(getFragmentView())
        super.onAttach(context)
    }

    override fun onDetach() {
        presenter.onViewDetached()
        super.onDetach()
    }

    fun getFragmentContext(): Context {
        return fragmentContext!!
    }

    @LayoutRes
    abstract fun getLayout(): Int

    abstract fun getFragmentView(): V?

    abstract fun initDagger(appComponent: AppComponent)
}