package com.example.imagesearch.presentation.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imagesearch.R
import com.example.imagesearch.databinding.FragmentMyBoxBinding
import com.example.imagesearch.presentation.entity.DocumentEntity

class MyBoxFragment : Fragment() {
    private var _binding: FragmentMyBoxBinding? = null
    private val binding get() = _binding!!
    private val pickedAdapter by lazy {
        ImageItemAdapter { image, position -> itemOnClick(image, position) }
    }
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
        _binding = FragmentMyBoxBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initView(){
        imageSearchViewModel.getPickedImageList()

        imageSearchViewModel.pickedImage.observe(requireActivity()) {
            pickedAdapter.itemList = it
            binding.rvPickedList.adapter?.notifyDataSetChanged()
        }

        binding.rvPickedList.apply {
            adapter = pickedAdapter
            layoutManager = GridLayoutManager(requireActivity(), 2)
        }
    }

    private fun itemOnClick(documentEntity: DocumentEntity, position: Int){
        MainActivity.PICKED_IMAGE.remove(documentEntity)
        pickedAdapter.apply{
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemCount)
        }
    }
    companion object {

        @JvmStatic
        fun newInstance() =
            MyBoxFragment().apply {
            }
    }
}