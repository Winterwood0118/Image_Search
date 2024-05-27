package com.example.imagesearch.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
@Dao
interface RemoteKeyDao {
    @Insert
    suspend fun insert(remoteKey: RemoteKey)

    @Query("SELECT * FROM remote_key ORDER BY nextPage DESC LIMIT 1")
    suspend fun getNextKey(): RemoteKey

    @Query("DELETE FROM remote_key")
    suspend fun deleteAll()
}