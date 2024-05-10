package com.example.imagesearch.presentation.repository

import com.example.imagesearch.presentation.entity.SearchModelEntity

interface SearchImageRepository {
    suspend fun getEntityList(searchWord: String) : SearchModelEntity
}