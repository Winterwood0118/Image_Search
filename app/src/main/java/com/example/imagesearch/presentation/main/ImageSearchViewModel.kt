package com.example.imagesearch.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imagesearch.presentation.entity.DocumentModel
import com.example.imagesearch.presentation.mapper.asEntity
import com.example.imagesearch.presentation.repository.CacheRepository
import com.example.imagesearch.presentation.repository.SearchImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageSearchViewModel @Inject constructor(
    private val searchImageRepository: SearchImageRepository,
    private val cacheRepository: CacheRepository) :
    ViewModel() {
    private val _getImageModel: MutableLiveData<List<DocumentModel>> = MutableLiveData()
    val imageModel: LiveData<List<DocumentModel>> get() = _getImageModel

    private val _getPickedImage: MutableLiveData<List<DocumentModel>> = MutableLiveData()

    val pickedImage: LiveData<List<DocumentModel>> get() = _getPickedImage

    private val _getLastSearchWord: MutableLiveData<String> = MutableLiveData()

    val lastSearchWord: LiveData<String> get() = _getLastSearchWord


    fun setLastSearchWord(searchWord: String) {
        _getLastSearchWord.value = searchWord
    }

    // 서버로부터 데이터를 받아올 때 마다 저장된 리스트에 중복된 섬네일 url있는지 검사해서 좋아요 값 변경
    fun getImageModelList(searchWord: String) = viewModelScope.launch {
        _getImageModel.value = searchImageRepository.getImageList(searchWord).items
    }

    fun getKLikeThumbnailList() = viewModelScope.launch {
        _getPickedImage.value = cacheRepository.getAllThumbnail()
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

    // 단순하게 해당 model의 value의 해당 인덱스의 좋아요 값을 바꾸는걸로는 observe패턴에서 변화로 감지하지 않아 새 모델을 할당
    fun switchLikeByUrl(url: String) {

    }

    //Uri 받아서 T/F 반환
    private fun findByUrl(url: String): Boolean {
        val currentImage = _getPickedImage.value?.find { it.thumbnailUrl == url }
        return currentImage != null
    }
}
