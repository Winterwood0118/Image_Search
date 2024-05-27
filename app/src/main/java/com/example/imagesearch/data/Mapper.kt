package com.example.imagesearch.data

import com.example.imagesearch.data.model.DocumentsResponse
import com.example.imagesearch.presentation.entity.DocumentModel

fun DocumentsResponse.toModel(): DocumentModel{
    return DocumentModel(
        thumbnailUrl, siteName, dateTime
    )
}