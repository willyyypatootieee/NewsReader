package com.example.mandirisubmission.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mandirisubmission.network.model.Article
import com.example.mandirisubmission.repository.NewsRepository
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {

    private val repository =
        NewsRepository()

    var newsList by mutableStateOf<List<Article>>(
        emptyList()
    )
        private set

    init {
        getNews()
    }

    private fun getNews() {

        viewModelScope.launch {

            val response =
                repository.getTopHeadlines()

            if (response.isSuccessful) {

                newsList =
                    response.body()?.articles
                        ?: emptyList()
            }
        }
    }
}