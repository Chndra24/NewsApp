package com.cwb.newsapp.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.cwb.newsapp.data.model.Article
import com.cwb.newsapp.utils.HeadlineFragment

class HeadlineAdapter(fragmentActivity: FragmentActivity, private val articles: List<Article>) :
    FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = articles.size

    override fun createFragment(position: Int): Fragment {
        return HeadlineFragment.newInstance(articles[position])
    }
}


