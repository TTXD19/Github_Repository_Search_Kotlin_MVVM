package com.android.practice.github_repository_search_kotlin_mvvm.adapter

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationListener() : RecyclerView.OnScrollListener() {

    var firstTime = false


    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (!recyclerView.canScrollVertically(View.LAYER_TYPE_SOFTWARE)){
            if (true){
                loadMoreItems()
            }
            firstTime = true
        }

        /*
        val mLayoutManager: LinearLayoutManager
        mLayoutManager = recyclerView.layoutManager as LinearLayoutManager
        val visibleItemCount = mLayoutManager.childCount
        val totalItemCount = mLayoutManager.itemCount
        val pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition()
        if (pastVisibleItems + visibleItemCount >= totalItemCount) {
            loadMoreItems()
        }

         */
    }

    abstract fun loadMoreItems()


}