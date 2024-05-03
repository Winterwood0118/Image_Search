package com.example.imagesearch.data.remote

import com.google.gson.annotations.SerializedName

data class ImageModel(
    @SerializedName("meta") val meta: Meta,
    @SerializedName("documents") val documents: List<Documents>
)

data class Documents(
    @SerializedName("thumbnail_url") val thumbnailUrl: String,
    @SerializedName("display_sitename") val siteName: String,
    @SerializedName("datetime") val dateTime: String
)

data class Meta(
    @SerializedName("is_end") val isEnd: Boolean,
    @SerializedName("pageable_count") val pageableCount: Int,
    @SerializedName("total_count") val totalCount: Int
)