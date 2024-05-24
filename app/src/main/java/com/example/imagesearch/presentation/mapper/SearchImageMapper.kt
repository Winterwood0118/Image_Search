package com.example.imagesearch.presentation.mapper

import com.example.imagesearch.data.remote.DocumentsResponse
import com.example.imagesearch.data.remote.ImageResponse
import com.example.imagesearch.data.remote.MetaResponse
import com.example.imagesearch.presentation.entity.DocumentModel
import com.example.imagesearch.presentation.entity.ImageModel
import com.example.imagesearch.presentation.entity.MetaModel

fun List<DocumentsResponse>.asModel(): List<DocumentModel> {
    return map {
        DocumentModel(
            it.thumbnailUrl,
            it.siteName,
            it.dateTime
        )
    }
}
fun MetaResponse.toModel(): MetaModel {
    return MetaModel(
        isEnd, pageableCount, totalCount
    )
}

fun ImageResponse.toModel() = ImageModel(
    meta = meta.toModel(),
    items = documents.asModel()
)