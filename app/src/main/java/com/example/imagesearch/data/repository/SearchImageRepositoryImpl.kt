package com.example.imagesearch.data.repository

import com.example.imagesearch.data.database.SearchRemoteDataSource
import com.example.imagesearch.presentation.entity.SearchModelEntity
import com.example.imagesearch.presentation.repository.SearchImageRepository
import com.example.imagesearch.presentation.mapper.toEntity

class SearchImageRepositoryImpl(
    private val dataSource: SearchRemoteDataSource
): SearchImageRepository {
    override suspend fun getEntityList(searchWord: String): SearchModelEntity {
        val imageList = dataSource.getImage(searchWord).toEntity()
        val videoList = dataSource.getVideo(searchWord).toEntity()

        return SearchModelEntity(
            imageList.meta,
            (imageList.items + videoList.items).sortedByDescending { it.dateTime }
        )
    }
}