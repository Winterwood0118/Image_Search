package com.example.imagesearch.data.remote

import com.google.gson.annotations.SerializedName

data class ImageModel(
    @SerializedName("meta") val meta: Meta,
    @SerializedName("documents") val documents: List<ImageDocuments>
)

data class ImageDocuments(
    @SerializedName("thumbnail_url") val thumbnailUrl: String,
    @SerializedName("display_sitename") val siteName: String,
    @SerializedName("datetime") val dateTime: String
)