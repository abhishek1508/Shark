package com.android.sharkteeth.feature.sharklist

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.android.sharkteeth.R
import com.android.sharkteeth.base.BaseActivity
import com.android.sharkteeth.di.AppComponent
import com.android.sharkteeth.feature.api.entity.Photo
import kotlinx.android.synthetic.main.activity_main.*

class SharkListActivity: BaseActivity<SharkListContract.SharkListPresenter, SharkListContract.SharkListView>(),
        SharkListContract.SharkListView, SharkListRecycler.IPhotoOnClickListener {

    private lateinit var adapter: SharkListRecycler
    private lateinit var manager: GridLayoutManager

    // BaseActivity ////////////////////////////////////////////////////////////////////////////////
    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun getView(): SharkListContract.SharkListView? = this

    override fun initView() {
        adapter = SharkListRecycler(this)
        manager = GridLayoutManager(this, 3)
        adapter.setCallback(this)
        photoRecycler.layoutManager = manager
        photoRecycler.adapter = adapter

        swipeRefreshLayout.setOnRefreshListener {
            presenter.getImages()
        }
    }

    // Appcompat activity //////////////////////////////////////////////////////////////////////////
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.getImages()
    }

    override fun initDagger(appComponent: AppComponent) {
        appComponent.sharkListBuilder().sharkListActivity(this).build().inject(this)
    }

    // SharkListContract.SharkListView methods /////////////////////////////////////////////////////
    override fun showImages(photoList: MutableList<Photo>) {
        if (swipeRefreshLayout.isRefreshing) {
            swipeRefreshLayout.isRefreshing = false
        }
        adapter.removeAllPhotos()
        adapter.addAllPhotos(photoList)
    }

    override fun errorReceived() {

    }

    // SharkListRecycler.IPhotoOnClickListener methods /////////////////////////////////////////////
    override fun onPhotoClicked(position: Int) {

    }
}