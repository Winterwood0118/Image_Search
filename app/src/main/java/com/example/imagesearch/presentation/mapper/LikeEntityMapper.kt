package com.example.imagesearch.presentation.mapper

import com.example.imagesearch.data.database.entity.LikeEntity
import com.example.imagesearch.presentation.entity.DocumentModel

fun DocumentModel.asEntity(): LikeEntity{
    return LikeEntity(
        siteName = siteName,
        thumbnailUrl = thumbnailUrl,
        dateTime = dateTime
    )
}