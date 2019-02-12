package com.android.sharkteeth.feature.sharklist

import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Parcelable
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.android.sharkteeth.R
import com.android.sharkteeth.base.BaseActivity
import com.android.sharkteeth.di.AppComponent
import com.android.sharkteeth.feature.api.entity.Photo
import kotlinx.android.synthetic.main.activity_main.*

const val STATE_RECYCLER = "state.recycler"

class SharkListActivity: BaseActivity<SharkListContract.SharkListPresenter, SharkListContract.SharkListView>(),
        SharkListContract.SharkListView, SharkListRecycler.IPhotoOnClickListener {

    private lateinit var adapter: SharkListRecycler
    private lateinit var manager: GridLayoutManager
    private var nextPageNumber: Int = 1
    private var totalPages: Int = -1
    private var isLoading: Boolean = false

    companion object {
        private val bundleRecyclerViewState: Bundle = Bundle()
    }

    // BaseActivity ////////////////////////////////////////////////////////////////////////////////
    override fun getLayout(): Int = R.layout.activity_main

    override fun getView(): SharkListContract.SharkListView? = this

    override fun initDagger(appComponent: AppComponent) {
        appComponent.sharkListBuilder().sharkListActivity(this).build().inject(this)
    }

    override fun initView() {
        Log.d("erereer", "initView")
        adapter = SharkListRecycler(this)
        manager = GridLayoutManager(this, 3)
        adapter.setCallback(this)
        photoRecycler.layoutManager = manager
        photoRecycler.adapter = adapter

        swipeRefreshLayout.setOnRefreshListener {
            nextPageNumber = 1
            isLoading = false
            presenter.getImages(nextPageNumber, false)
        }

        infiniteScroll()
    }

    // Appcompat activity //////////////////////////////////////////////////////////////////////////
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("erereer", "onCreate")
        presenter.getImages(nextPageNumber, false)
    }

    override fun onPause() {
        super.onPause()
        bundleRecyclerViewState.putParcelable(STATE_RECYCLER, photoRecycler?.layoutManager?.onSaveInstanceState())
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        Log.d("erereer", "onConfigurationChanged")
        photoRecycler.layoutManager?.onRestoreInstanceState(bundleRecyclerViewState.getParcelable<Parcelable>(STATE_RECYCLER))
        val spanCount = when(newConfig?.orientation) {
            Configuration.ORIENTATION_PORTRAIT -> 3
            else -> 6
        }
        manager.spanCount = spanCount
        photoRecycler.layoutManager = manager
    }

    // Private methods /////////////////////////////////////////////////////////////////////////////
    private fun infiniteScroll() {
        photoRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                Log.d("endlessscroll", "last visible position: ${manager.findLastCompletelyVisibleItemPosition()}, " +
                        "total count: ${manager.itemCount - 10}")

                if (manager.findLastCompletelyVisibleItemPosition() >= manager.itemCount - 10
                                    && nextPageNumber < totalPages && !isLoading) {
                    isLoading = true
                    presenter.getImages(nextPageNumber, true)
                }
            }
        })
    }

    // SharkListContract.SharkListView methods /////////////////////////////////////////////////////
    override fun showImages(photoList: MutableList<Photo>, loadMore: Boolean) {
        if (swipeRefreshLayout.isRefreshing) {
            swipeRefreshLayout.isRefreshing = false
        }
        if (!loadMore) {
            adapter.removeAllPhotos()
            adapter.addAllPhotos(photoList)
        } else {
            adapter.addMorePhotos(photoList)
            isLoading = false
        }
    }

    override fun nextPage(currentPageNo: Int, totalPages: Int) {
        this.totalPages = totalPages
        this.nextPageNumber = currentPageNo+1
    }

    override fun errorReceived() {

    }

    // SharkListRecycler.IPhotoOnClickListener methods /////////////////////////////////////////////
    override fun onPhotoClicked(photo: Photo) {
        startActivity(getLightboxIntent(photo))
    }
}