package com.example.imagesearch.data.repository

import com.example.imagesearch.data.remote.SearchRemoteDataSource
import com.example.imagesearch.presentation.entity.ImageModel
import com.example.imagesearch.presentation.repository.SearchImageRepository
import com.example.imagesearch.presentation.mapper.toModel
import javax.inject.Inject

class SearchImageRepositoryImpl @Inject constructor(
    private val dataSource: SearchRemoteDataSource
) : SearchImageRepository {
    override suspend fun getImageList(searchWord: String): ImageModel {
        return dataSource.getImage(searchWord).toModel()
    }
}