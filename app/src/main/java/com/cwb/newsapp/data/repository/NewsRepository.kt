package com.cwb.newsapp.data.repository

import com.cwb.newsapp.data.api.NewsApiService
import com.cwb.newsapp.data.model.Article

class NewsRepository(private val api: NewsApiService, private val apiKey: String) {

    suspend fun getTopHeadlines(): List<Article> {
        return api.getTopHeadlines(apiKey).articles
    }

    suspend fun getAllNews(query: String, page: Int): List<Article> {
        return api.getAllNews(apiKey, query, page).articles
    }

}
