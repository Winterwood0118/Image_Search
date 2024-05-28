package com.example.imagesearch.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.imagesearch.data.database.LikeRoomDataBase
import com.example.imagesearch.data.database.RemoteKey
import com.example.imagesearch.data.model.DocumentsResponse
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class SearchRemoteMediator @Inject constructor(
    private val dataBase: LikeRoomDataBase,
    private val searchRemoteDataSource: SearchRemoteDataSource
): RemoteMediator<Int, DocumentsResponse>(){
    val responseDao = dataBase.responseDao()
    val remoteKeyDao = dataBase.remoteKeyDao()
    var searchWord = ""
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, DocumentsResponse>
    ): MediatorResult {
        val remoteKey = when(loadType){
            LoadType.REFRESH -> {
                null
            }
            LoadType.PREPEND -> {
                return MediatorResult.Success(true)
            }
            LoadType.APPEND -> {
                remoteKeyDao.getNextKey()
            }
        }

        try {
            val page = remoteKey?.nextPage ?: 1
            val loadSize = 25
            val response = searchRemoteDataSource.getImage(searchWord, loadSize)
            val responseList = response.documents
            if (loadType == LoadType.REFRESH){
                responseDao.deleteAll()
                remoteKeyDao.deleteAll()
            }
            remoteKeyDao.insert(RemoteKey(nextPage = page + 1))
            responseDao.insertAll(responseList)

            return MediatorResult.Success(responseList.isEmpty())
        }catch (e: Exception){
            return MediatorResult.Error(e)
        }
    }
}