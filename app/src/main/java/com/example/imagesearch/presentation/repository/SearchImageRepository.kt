package com.example.imagesearch.presentation.repository

import com.example.imagesearch.presentation.entity.ImageModel

interface SearchImageRepository {
    suspend fun getImageList(searchWord: String) : ImageModel
}