package com.example.imagesearch.presentation.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.room.Query
import com.example.imagesearch.presentation.entity.DocumentModel
import com.example.imagesearch.presentation.mapper.asEntity
import com.example.imagesearch.presentation.repository.CacheRepository
import com.example.imagesearch.presentation.repository.SearchImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageSearchViewModel @Inject constructor(
    private val searchImageRepository: SearchImageRepository,
    private val cacheRepository: CacheRepository) :
    ViewModel() {

    private val _getLastSearchWord: MutableLiveData<String> = MutableLiveData()

    val lastSearchWord: LiveData<String> get() = _getLastSearchWord

    private val queryFlow = MutableSharedFlow<String>()

    val likedImage = mutableSetOf<DocumentModel>()
    private val _likedFlow = MutableSharedFlow<List<DocumentModel>>(replay = 1)
    val likedFlow get() = _likedFlow.asSharedFlow()

    fun setLikedImage(documentModel: DocumentModel){
        likedImage.add(documentModel)
        viewModelScope.launch {
            _likedFlow.emit(likedImage.toList())
        }
    }

    fun setQuery(query: String){
        viewModelScope.launch{
            Log.d("ImageSearchViewModel", "Query received: $query")
            queryFlow.emit(query)
            queryFlow.collect {
                Log.d("ImageSearchViewModel", "emitted: $it")
            }

        }
    }

    val pagingDataFlow = queryFlow.flatMapLatest { s ->
        searchImageRepository.getPagingImageList(s).also { pagingDataFlow1 ->
            pagingDataFlow1.collect{
                Log.d("ImageSearchViewModel", "pagingDataFlow: $it")
            }
        }
    }.cachedIn(viewModelScope)

    fun setLastSearchWord(searchWord: String) {
        _getLastSearchWord.value = searchWord
    }

    fun pickImage(documentEntity: DocumentModel) {
        viewModelScope.launch{
            cacheRepository.insertThumbnail(documentEntity.asEntity())
        }
    }

    fun removeImage(documentEntity: DocumentModel) {
        viewModelScope.launch{
            cacheRepository.deleteThumbnail(documentEntity.asEntity())
        }
    }

}
