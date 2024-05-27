package com.example.imagesearch.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.imagesearch.data.model.DocumentsResponse

@Dao
interface ResponseDao {
    @Upsert
    fun insertAll(list: List<DocumentsResponse>)

    @Query("DELETE FROM documentsResponse")
    fun deleteAll()

    @Query("SELECT * FROM documentsResponse")
    fun getAll(): PagingSource<Int, DocumentsResponse>
}