package com.example.imagesearch.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.imagesearch.data.repository.SearchImageRepositoryImpl
import com.example.imagesearch.network.RetrofitClient
import com.example.imagesearch.presentation.entity.DocumentEntity
import com.example.imagesearch.presentation.repository.SearchImageRepository
import kotlinx.coroutines.launch

class ImageSearchViewModel(private val searchImageRepository: SearchImageRepository) : ViewModel() {
    private val _getImageModel: MutableLiveData<List<DocumentEntity>> = MutableLiveData()
    val imageModel: LiveData<List<DocumentEntity>> get() = _getImageModel


    private val _getPickedImage: MutableLiveData<List<DocumentEntity>> = MutableLiveData()

    val pickedImage: LiveData<List<DocumentEntity>> get() = _getPickedImage

    private val _getLastSearchWord: MutableLiveData<String> = MutableLiveData()

    val lastSearchWord: LiveData<String> get() = _getLastSearchWord

    fun setLastSearchWord(searchWord: String){
        _getLastSearchWord.value = searchWord
    }

    fun getImageModelList(searchWord: String) = viewModelScope.launch {
        _getImageModel.value = searchImageRepository.getEntityList(searchWord).items.map {
            DocumentEntity(
                thumbnailUrl = it.thumbnailUrl,
                dateTime = it.dateTime,
                siteName = it.siteName,
                isLike = findByUrl(it.thumbnailUrl),
                type = it.type
            )
        }
    }


    fun pickImage(documentEntity: DocumentEntity) {
        val updateList = (_getPickedImage.value?: mutableListOf()).toMutableList()
        updateList.add(documentEntity)
        _getPickedImage.value = updateList
    }

    fun removeImage(documentEntity: DocumentEntity) {
        val updateList = (_getPickedImage.value?: mutableListOf()).toMutableList()
        updateList.remove(documentEntity)
        _getPickedImage.value = updateList
    }

    fun switchLikeByUrl(url: String){
        if(_getImageModel.value != null){
            val newModel = _getImageModel.value?: mutableListOf()
            val currentImage = newModel.find { it.thumbnailUrl == url }
            currentImage?.let {
                val idx = newModel.indexOf(currentImage)
                newModel.let{
                    it[idx].isLike = false
                }
                _getImageModel.value = newModel
            }
        }
    }

    private fun findByUrl(url: String): Boolean{
        val currentImage = _getPickedImage.value?.find { it.thumbnailUrl == url }
        return currentImage != null
    }
}

class ImageSearchViewModelFactory : ViewModelProvider.Factory {
    private val repository = SearchImageRepositoryImpl(RetrofitClient.searchKakaoImage)

    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras
    ): T {
        return ImageSearchViewModel(
            repository
        ) as T
    }
}