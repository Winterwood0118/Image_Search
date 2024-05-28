package com.example.imagesearch.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imagesearch.databinding.FragmentMyBoxBinding
import com.example.imagesearch.presentation.entity.DocumentModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint

class MyBoxFragment : Fragment() {
    private var _binding: FragmentMyBoxBinding? = null
    private val binding get() = _binding!!
    private val pickedAdapter by lazy {
        ImageItemAdapter { image, position -> itemOnClick(image, position) }
    }
    private val imageSearchViewModel by activityViewModels<ImageSearchViewModel> ()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyBoxBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView(){
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            imageSearchViewModel.likedFlow.collectLatest {
                pickedAdapter.itemList = it
                binding.rvPickedList.adapter = pickedAdapter
            }
        }

        binding.rvPickedList.apply {
            adapter = pickedAdapter
            layoutManager = GridLayoutManager(requireActivity(), 2)
        }
    }

    private fun itemOnClick(documentEntity: DocumentModel, position: Int){
        imageSearchViewModel.apply{
            removeImage(documentEntity)
        }
        pickedAdapter.notifyItemRemoved(position)
    }
}