package com.example.imagesearch.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.imagesearch.data.model.DocumentsResponse

@Dao
interface ResponseDao {
    @Upsert
    suspend fun insertAll(list: List<DocumentsResponse>)

    @Query("DELETE FROM documentResponse")
    suspend fun deleteAll()

    @Query("SELECT * FROM documentResponse")
    fun getAll(): PagingSource<Int, DocumentsResponse>
}