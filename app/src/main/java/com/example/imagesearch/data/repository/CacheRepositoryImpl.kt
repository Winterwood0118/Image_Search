package com.example.imagesearch.data.repository

import com.example.imagesearch.data.database.LikeDao
import com.example.imagesearch.data.database.entity.LikeEntity
import com.example.imagesearch.presentation.entity.DocumentModel
import com.example.imagesearch.presentation.repository.CacheRepository
import javax.inject.Inject

class CacheRepositoryImpl @Inject constructor(
    private val likeDao: LikeDao
)
    : CacheRepository {
    override suspend fun insertThumbnail(likeEntity: LikeEntity) {
        likeDao.insertLikeThumbnail(likeEntity)
    }

    override suspend fun deleteThumbnail(likeEntity: LikeEntity) {
        likeDao.deleteThumbNail(likeEntity)
    }

    override suspend fun getAllThumbnail(): List<DocumentModel>{
        return likeDao.getAllThumbnails().map {
            DocumentModel(
                thumbnailUrl = it.thumbnailUrl,
                dateTime = it.dateTime,
                siteName = it.siteName
            )
        }
    }
}