package com.example.imagesearch.presentation.mapper

import com.example.imagesearch.data.remote.Documents
import com.example.imagesearch.data.remote.ImageModel
import com.example.imagesearch.presentation.entity.DocumentEntity
import com.example.imagesearch.presentation.entity.ImageModelEntity

fun List<Documents>.asEntity(): List<DocumentEntity> {
    return map {
        DocumentEntity(
            it.thumbnailUrl,
            it.siteName,
            it.dateTime
        )
    }
}

fun ImageModel.toEntity() = ImageModelEntity(
    meta = meta,
    items = documents.asEntity()
)