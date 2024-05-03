package com.example.imagesearch.data.repository

import com.example.imagesearch.data.database.SearchRemoteDataSource
import com.example.imagesearch.presentation.entity.ImageModelEntity
import com.example.imagesearch.presentation.repository.SearchImageRepository
import com.example.imagesearch.presentation.mapper.toEntity

class SearchImageRepositoryImpl(
    private val dataSource: SearchRemoteDataSource
): SearchImageRepository {
    override suspend fun getImageList(searchWord: String): ImageModelEntity {
        return dataSource.getImage(searchWord).toEntity()
    }
}