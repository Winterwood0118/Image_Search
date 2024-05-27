package com.example.imagesearch.data.database.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class LikeEntity(
    @PrimaryKey
    val thumbnailUrl: String,
    val siteName: String,
    val dateTime: String,
): Parcelable