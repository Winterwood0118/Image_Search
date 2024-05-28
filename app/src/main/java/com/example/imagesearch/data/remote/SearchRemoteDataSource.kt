package com.example.imagesearch.data.remote

import com.example.imagesearch.data.model.ImageResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchRemoteDataSource {
    @GET("v2/search/image")
    suspend fun getImage(
        @Query("query") searchWord: String,
        @Query("pageable_count") page: Int = 4,
        @Query("total_count") size: Int = 25
    ): ImageResponse
}