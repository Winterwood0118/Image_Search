package com.example.imagesearch.network

import com.example.imagesearch.data.database.SearchRemoteDataSource
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://dapi.kakao.com/"

    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val searchKakaoImage : SearchRemoteDataSource by lazy {
        retrofit.create(SearchRemoteDataSource::class.java)
    }
}