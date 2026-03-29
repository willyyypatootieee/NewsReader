package com.example.mandirisubmission.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val api: NewsInterface by lazy {

        Retrofit.Builder()
            .baseUrl(NewsInterface.BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .build()
            .create(NewsInterface::class.java)

    }

}