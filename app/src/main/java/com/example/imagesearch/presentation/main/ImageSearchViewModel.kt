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

class ImageSearchViewModel(private val searchImageRepository: SearchImageRepository): ViewModel() {
    private val _getImageModel: MutableLiveData<List<DocumentEntity>> = MutableLiveData()
    val imageModel: LiveData<List<DocumentEntity>> get() = _getImageModel

    fun getImageModelList(searchWord: String) = viewModelScope.launch {
        _getImageModel.value = searchImageRepository.getImageList(searchWord).items
    }
}

class ImageSearchViewModelFactory: ViewModelProvider.Factory {
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