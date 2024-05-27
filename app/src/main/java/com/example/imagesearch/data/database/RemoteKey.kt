package com.example.imagesearch.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("remote_key")
data class RemoteKey(
    @PrimaryKey val nextPage: Int
)
