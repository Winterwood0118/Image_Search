package com.example.imagesearch.data.database.di

import com.example.imagesearch.data.repository.CacheRepositoryImpl
import com.example.imagesearch.presentation.repository.CacheRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface CacheModule {
    @Binds
    fun bindingCacheRepository(cacheRepositoryImpl: CacheRepositoryImpl): CacheRepository
}