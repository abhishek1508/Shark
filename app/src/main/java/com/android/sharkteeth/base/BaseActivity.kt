package com.android.sharkteeth.base

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import com.android.sharkteeth.App
import com.android.sharkteeth.di.AppComponent
import javax.inject.Inject

abstract class BaseActivity<P: BasePresenter<V>, V: BaseView>: AppCompatActivity() {

    @Inject
    lateinit var presenter: P
        internal set
    abstract fun getView(): V?
    abstract fun initDagger(appComponent: AppComponent)
    abstract fun initView()
    @LayoutRes abstract fun getLayout(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        initDagger((application as App).appComponent)
        presenter.onViewAttached(getView())
        initView()
    }

    override fun onDestroy() {
        presenter.onViewDetached()
        super.onDestroy()
    }

    fun setToolbar(appBar: Toolbar) {

    }
}