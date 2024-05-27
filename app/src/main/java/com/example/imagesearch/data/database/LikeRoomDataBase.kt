package com.example.imagesearch.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.imagesearch.data.database.entity.LikeEntity
import com.example.imagesearch.data.model.DocumentsResponse

@Database(
    entities = [
        LikeEntity::class,
        DocumentsResponse::class,
        RemoteKey::class
    ],
    version = 1
)
abstract class LikeRoomDataBase() : RoomDatabase() {
    abstract fun likeDao(): LikeDao
    abstract fun remoteKeyDao(): RemoteKeyDao
    abstract fun responseDao(): ResponseDao
}