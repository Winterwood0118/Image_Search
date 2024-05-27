package com.example.imagesearch.presentation.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imagesearch.databinding.FragmentImageSearchBinding
import com.example.imagesearch.presentation.entity.DocumentModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint

class ImageSearchFragment : Fragment() {
    private var _binding: FragmentImageSearchBinding? = null
    private val binding get() = _binding!!

    private val imageSearchViewModel by activityViewModels<ImageSearchViewModel>()

    private val searchPagingAdapter by lazy {
        SearchPagingAdapter { documentEntity, position ->
            itemOnClick(documentEntity, position)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
    }

    private fun initView() {
        binding.etSearch.apply {
            setText(imageSearchViewModel.lastSearchWord.value)
            doAfterTextChanged {
                imageSearchViewModel.setQuery(text.trim().toString())
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun itemOnClick(documentEntity: DocumentModel, position: Int) {
        imageSearchViewModel.apply {
            pickImage(documentEntity)
        }
    }

    private fun initViewModel(){
        binding.rvImageList.apply {
            adapter = searchPagingAdapter
            layoutManager = GridLayoutManager(requireActivity(), 2)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            Log.d("ImageSearchFragment", "Paging data collected?")
            imageSearchViewModel.pagingDataFlow.collect {
                searchPagingAdapter.submitData(it)
                Log.d("ImageSearchFragment", "${searchPagingAdapter.itemCount}")
            }
        }
    }
}
