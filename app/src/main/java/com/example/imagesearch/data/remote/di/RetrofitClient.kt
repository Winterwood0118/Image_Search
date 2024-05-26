package com.example.imagesearch.data.remote.di

import com.example.imagesearch.data.remote.SearchRemoteDataSource
import com.example.imagesearch.network.AuthorizationInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
internal object RetrofitClient {
    private const val BASE_URL = "https://dapi.kakao.com/"

    @Provides
    fun provideOkhttpClient(): OkHttpClient{
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addNetworkInterceptor(loggingInterceptor)
            .addInterceptor(AuthorizationInterceptor())
            .build()
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides()
    fun provideSearchRemoteDataSource(retrofit: Retrofit): SearchRemoteDataSource {
        return retrofit.create(SearchRemoteDataSource::class.java)
    }
}