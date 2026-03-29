package com.example.mandirisubmission.network

import com.example.mandirisubmission.network.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsInterface {
    companion object {
        const val API_KEY = "5e2660dffd09447884bb31ee5fedce5d"
        const val BASE_URL = "https://newsapi.org/"
    }

    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "us",
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<NewsResponse>
}
