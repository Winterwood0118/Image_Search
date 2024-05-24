package com.example.imagesearch.presentation.entity

import com.example.imagesearch.data.remote.MetaResponse

data class ImageModel(
    val meta: MetaModel,
    val items: List<DocumentModel>
)
data class DocumentModel(
    val thumbnailUrl: String,
    val siteName: String,
    val dateTime: String,
    var isLike: Boolean = false
)
data class MetaModel(
    val isEnd: Boolean,
    val pageableCount: Int,
    val totalCount: Int
)
