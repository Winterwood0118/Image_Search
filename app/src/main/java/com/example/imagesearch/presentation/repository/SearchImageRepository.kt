package com.example.imagesearch.presentation.repository

import com.example.imagesearch.presentation.entity.ImageModelEntity

interface SearchImageRepository {
    suspend fun getEntityList(searchWord: String) : ImageModelEntity
}