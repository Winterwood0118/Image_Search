package com.example.imagesearch.data.database

import com.example.imagesearch.data.remote.ImageModel
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SearchRemoteDataSource {
    @GET("v2/search/image")
    suspend fun getImage(
        @Query("query") searchWord: String,
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 80,
        @Header("Authorization") key: String = "KakaoAK 9982187f033a2c4ec2ff283fb4ac687c"
    ): ImageModel
}