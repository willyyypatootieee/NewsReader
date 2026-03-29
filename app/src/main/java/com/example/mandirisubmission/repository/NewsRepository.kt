package com.example.mandirisubmission.repository

import com.example.mandirisubmission.network.RetrofitInstance

class NewsRepository {
    suspend fun getTopHeadlines() =
        RetrofitInstance.api.getTopHeadlines();
}