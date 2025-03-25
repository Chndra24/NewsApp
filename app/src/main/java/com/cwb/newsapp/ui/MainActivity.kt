package com.cwb.newsapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.cwb.newsapp.R
import com.cwb.newsapp.data.api.NewsApiService
import com.cwb.newsapp.data.api.RetrofitInstance
import com.cwb.newsapp.data.repository.NewsRepository
import com.cwb.newsapp.ui.adapter.HeadlineAdapter
import com.cwb.newsapp.ui.adapter.NewsAdapter
import com.cwb.newsapp.ui.viewmodel.NewsViewModel
import com.cwb.newsapp.ui.viewmodel.NewsViewModelFactory
import com.cwb.newsapp.utils.DepthPageTransformer
import com.cwb.newsapp.utils.EndlessScrollListener
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var headlineAdapter: HeadlineAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Get Retrofit instance from the RetrofitModule
        val retrofit = RetrofitInstance.getRetrofitInstance()

        val service = retrofit.create(NewsApiService::class.java)
        val repository = NewsRepository(service, "a150f07e68b34f058fd729d170a3bdbc")
        viewModel = ViewModelProvider(this, NewsViewModelFactory(repository))[NewsViewModel::class.java]

        setupRecyclerView()
        observeViewModel()

        viewModel.loadHeadlines()
        viewModel.loadNews("technology")
    }

    private fun setupRecyclerView() {
        // ViewPager2 for swipeable headlines
        val viewPagerHeadlines = findViewById<ViewPager2>(R.id.viewPagerHeadlines)
        viewPagerHeadlines.setPageTransformer(DepthPageTransformer())

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)

        viewModel.headlines.observe(this) { headlines ->
            headlineAdapter = HeadlineAdapter(this, headlines)
            viewPagerHeadlines.adapter = headlineAdapter

            TabLayoutMediator(tabLayout, viewPagerHeadlines) { _, _ -> }.attach()
        }

        newsAdapter = NewsAdapter(emptyList())
        val newsList = findViewById<RecyclerView>(R.id.rvNewsList)
        newsList.layoutManager = LinearLayoutManager(this)
        newsList.adapter = newsAdapter

        newsList.addOnScrollListener(object : EndlessScrollListener() {
            override fun onLoadMore() {
                viewModel.loadNews("technology")
            }
        })
    }

    private fun observeViewModel() {
        viewModel.newsList.observe(this) {
            newsAdapter.updateData(it)
        }
    }
}

