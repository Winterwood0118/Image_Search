package com.example.imagesearch.data.remote.di

import com.example.imagesearch.data.repository.SearchImageRepositoryImpl
import com.example.imagesearch.presentation.repository.SearchImageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface SearchModule {
    @Binds
    fun bindingSearchRepository(searchImageRepositoryImpl: SearchImageRepositoryImpl): SearchImageRepository
}