package com.cwb.newsapp.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class EndlessScrollListener : RecyclerView.OnScrollListener() {
    override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
        val lm = rv.layoutManager as LinearLayoutManager
        if (lm.findLastVisibleItemPosition() == lm.itemCount - 1) {
            onLoadMore()
        }
    }
    abstract fun onLoadMore()
}