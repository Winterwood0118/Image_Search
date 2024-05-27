package com.example.imagesearch.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.imagesearch.data.database.entity.LikeEntity

@Dao
interface LikeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLikeThumbnail(likeEntity: LikeEntity)

    @Query("SELECT*FROM LikeEntity")
    suspend fun getAllThumbnails(): List<LikeEntity>

    @Delete
    suspend fun deleteThumbNail(likeEntity: LikeEntity)
}