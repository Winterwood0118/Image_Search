package com.example.imagesearch.data.remote

import com.google.gson.annotations.SerializedName

data class VideoModel(
    @SerializedName("meta") val meta: Meta,
    @SerializedName("documents") val documents: List<VideoDocuments>
)

data class VideoDocuments(
    @SerializedName("thumbnail") val thumbnailUrl: String,
    @SerializedName("author") val siteName: String,
    @SerializedName("datetime") val dateTime: String
)