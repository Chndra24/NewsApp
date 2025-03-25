package com.cwb.newsapp.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cwb.newsapp.data.model.Article
import com.cwb.newsapp.data.repository.NewsRepository
import kotlinx.coroutines.launch

class NewsViewModel(private val repository: NewsRepository) : ViewModel() {

    private val _headlines = MutableLiveData<List<Article>>()
    val headlines: LiveData<List<Article>> = _headlines

    private val _newsList = MutableLiveData<List<Article>>()
    val newsList: LiveData<List<Article>> = _newsList

    private var currentPage = 1
    private var isLoading = false

    fun loadHeadlines() {
        viewModelScope.launch {
            _headlines.value = repository.getTopHeadlines()
        }
    }

    fun loadNews(query: String) {
        if (isLoading) return
        isLoading = true
        viewModelScope.launch {
            val result = repository.getAllNews(query, currentPage)
            val updated = (_newsList.value ?: emptyList()) + result
            _newsList.value = updated
            currentPage++
            isLoading = false
        }
    }
}