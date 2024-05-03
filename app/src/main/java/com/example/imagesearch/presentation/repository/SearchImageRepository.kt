package com.example.imagesearch.presentation.repository

import com.example.imagesearch.presentation.entity.ImageModelEntity

interface SearchImageRepository {
    suspend fun getImageList(searchWord: String) : ImageModelEntity
}