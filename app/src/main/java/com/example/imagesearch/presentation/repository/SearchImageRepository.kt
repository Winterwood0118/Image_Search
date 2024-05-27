package com.example.imagesearch.presentation.repository

import androidx.paging.PagingData
import com.example.imagesearch.presentation.entity.DocumentModel
import com.example.imagesearch.presentation.entity.ImageModel
import kotlinx.coroutines.flow.Flow

interface SearchImageRepository {
    suspend fun getImageList(searchWord: String) : ImageModel
    suspend fun getPagingImageList(searchWord: String): Flow<PagingData<DocumentModel>>
}