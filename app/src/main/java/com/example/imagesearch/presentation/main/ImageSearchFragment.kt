package com.example.imagesearch.presentation.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imagesearch.R
import com.example.imagesearch.databinding.FragmentImageSearchBinding


class ImageSearchFragment : Fragment() {
    private var _binding: FragmentImageSearchBinding? = null
    private val binding get() = _binding!!
    lateinit var imageItemAdapter: ImageItemAdapter

    private val imageSearchViewModel by viewModels<ImageSearchViewModel> {
        ImageSearchViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        imageItemAdapter = ImageItemAdapter()
        _binding = FragmentImageSearchBinding.inflate(inflater, container, false)
        binding.btnSearch.setOnClickListener {
            val string = binding.etSearch.text.toString().let {
                if (it == "") " "
                else it
            }
            Log.d("test123", "$string")
            imageSearchViewModel.getImageModelList(string)
            imageItemAdapter.itemList = imageSearchViewModel.imageModel.value?: listOf()
            binding.rvImageList.adapter?.notifyDataSetChanged()
        }
        binding.rvImageList.apply {
            adapter = imageItemAdapter
            layoutManager = GridLayoutManager(requireActivity(), 2)
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ImageSearchFragment().apply {
            }
    }
}
