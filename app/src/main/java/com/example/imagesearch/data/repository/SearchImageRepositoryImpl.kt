package com.example.imagesearch.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.imagesearch.data.database.LikeRoomDataBase
import com.example.imagesearch.data.remote.SearchRemoteDataSource
import com.example.imagesearch.data.remote.SearchRemoteMediator
import com.example.imagesearch.data.toModel
import com.example.imagesearch.presentation.entity.DocumentModel
import com.example.imagesearch.presentation.entity.ImageModel
import com.example.imagesearch.presentation.repository.SearchImageRepository
import com.example.imagesearch.presentation.mapper.toModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchImageRepositoryImpl @Inject constructor(
    private val dataSource: SearchRemoteDataSource,
    private val mediator: SearchRemoteMediator,
    private val dataBase: LikeRoomDataBase
) : SearchImageRepository {
    override suspend fun getImageList(searchWord: String): ImageModel {
        return dataSource.getImage(searchWord).toModel()
    }

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getPagingImageList(searchWord: String): Flow<PagingData<DocumentModel>> {
        Log.d("paging", "페이징 해씀")
        return Pager(
            config = PagingConfig(
                pageSize = 25
            ),
            remoteMediator = mediator.apply { this.searchWord = searchWord },
            pagingSourceFactory = {
                dataBase.responseDao().getAll()
            }
        ).flow.map { pagingData ->
            pagingData.map { documentsResponse ->
                documentsResponse.toModel().also { Log.d("paging", "Log: ${it.thumbnailUrl}") }
            }
        }

    }
}