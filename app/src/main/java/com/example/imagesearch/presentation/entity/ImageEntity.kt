package com.example.imagesearch.presentation.entity

import com.example.imagesearch.data.remote.Meta

data class SearchModelEntity(
    val meta: Meta,
    val items: List<DocumentEntity>
)
data class DocumentEntity(
    val thumbnailUrl: String,
    val siteName: String,
    val dateTime: String,
    var isLike: Boolean = false,
    val type: String
)
