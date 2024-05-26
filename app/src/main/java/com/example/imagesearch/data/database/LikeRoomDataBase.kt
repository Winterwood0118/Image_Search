package com.example.imagesearch.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.imagesearch.data.database.entity.LikeEntity

@Database(
    entities = [
        LikeEntity::class
    ],
    version = 1
)
abstract class LikeRoomDataBase():RoomDatabase() {
    abstract fun likeDao(): LikeDao
}