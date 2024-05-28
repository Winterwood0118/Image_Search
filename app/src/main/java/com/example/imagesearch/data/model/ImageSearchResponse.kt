package com.example.imagesearch.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class ImageResponse(
    @SerializedName("meta") val meta: MetaResponse,
    @SerializedName("documents") val documents: List<DocumentsResponse>
)
@Entity(tableName = "documentResponse")
data class DocumentsResponse(
    @PrimaryKey(autoGenerate = true) val uid: Long,
    @SerializedName("thumbnail_url") val thumbnailUrl: String,
    @SerializedName("display_sitename") val siteName: String,
    @SerializedName("datetime") val dateTime: String
)

data class MetaResponse(
    @SerializedName("is_end") val isEnd: Boolean,
    @SerializedName("pageable_count") val pageableCount: Int,
    @SerializedName("total_count") val totalCount: Int
)