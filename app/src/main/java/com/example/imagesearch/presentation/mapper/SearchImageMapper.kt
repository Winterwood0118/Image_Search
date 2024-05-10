package com.example.imagesearch.presentation.mapper

import com.example.imagesearch.data.remote.ImageDocuments
import com.example.imagesearch.data.remote.ImageModel
import com.example.imagesearch.data.remote.VideoDocuments
import com.example.imagesearch.data.remote.VideoModel
import com.example.imagesearch.presentation.entity.DocumentEntity
import com.example.imagesearch.presentation.entity.SearchModelEntity

fun List<ImageDocuments>.asImageEntity(): List<DocumentEntity> {
    return map {
        DocumentEntity(
            it.thumbnailUrl,
            it.siteName,
            it.dateTime,
            type = "Image"
        )
    }
}
fun List<VideoDocuments>.asVideoEntity(): List<DocumentEntity> {
    return map {
        DocumentEntity(
            it.thumbnailUrl,
            it.title,
            it.dateTime,
            type = "Video"
        )
    }
}

fun ImageModel.toEntity() = SearchModelEntity(
    meta = meta,
    items = documents.asImageEntity()
)

fun VideoModel.toEntity() = SearchModelEntity(
    meta = meta,
    items = documents.asVideoEntity()
)