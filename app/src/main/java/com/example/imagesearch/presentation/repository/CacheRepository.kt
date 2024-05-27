package com.example.imagesearch.presentation.repository

import com.example.imagesearch.data.database.entity.LikeEntity
import com.example.imagesearch.presentation.entity.DocumentModel

interface CacheRepository {
    suspend fun insertThumbnail(likeEntity: LikeEntity)

    suspend fun deleteThumbnail(likeEntity: LikeEntity)

    suspend fun getAllThumbnail(): List<DocumentModel>
}