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

    fun setLastSearchWord(searchWord: String) {
        _getLastSearchWord.value = searchWord
    }

    // 서버로부터 데이터를 받아올 때 마다 저장된 리스트에 중복된 섬네일 url있는지 검사해서 좋아요 값 변경
    fun getImageModelList(searchWord: String) = viewModelScope.launch {
        _getImageModel.value = searchImageRepository.getImageList(searchWord).items.map {
            DocumentEntity(
                thumbnailUrl = it.thumbnailUrl,
                dateTime = it.dateTime,
                siteName = it.siteName,
                isLike = findByUrl(it.thumbnailUrl)
            )
        }
    }


    fun pickImage(documentEntity: DocumentEntity) {
        val updateList = (_getPickedImage.value ?: mutableListOf()).toMutableList()
        updateList.add(documentEntity)
        _getPickedImage.value = updateList
    }

    fun removeImage(documentEntity: DocumentEntity) {
        val updateList = (_getPickedImage.value ?: mutableListOf()).toMutableList()
        updateList.remove(documentEntity)
        _getPickedImage.value = updateList
    }

    // 단순하게 해당 model의 value의 해당 인덱스의 좋아요 값을 바꾸는걸로는 observe패턴에서 변화로 감지하지 않아 새 모델을 할당
    fun switchLikeByUrl(url: String) {
        if (_getImageModel.value != null) {
            val newModel = _getImageModel.value ?: mutableListOf()
            val currentImage = newModel.find { it.thumbnailUrl == url }
            currentImage?.let {
                val idx = newModel.indexOf(currentImage)
                newModel.let {
                    it[idx].isLike = false
                }
                _getImageModel.value = newModel
            }
        }
    }
    //Uri 받아서 T/F 반환
    private fun findByUrl(url: String): Boolean {
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