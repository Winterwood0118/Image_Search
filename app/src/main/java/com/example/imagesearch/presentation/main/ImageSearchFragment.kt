package com.example.imagesearch.presentation.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.imagesearch.databinding.FragmentImageSearchBinding
import com.example.imagesearch.presentation.entity.DocumentEntity
import com.example.imagesearch.presentation.entity.ImageModelEntity
import okhttp3.internal.notify


class ImageSearchFragment : Fragment() {
    private var _binding: FragmentImageSearchBinding? = null
    private val binding get() = _binding!!
    private val imageItemAdapter: ImageItemAdapter by lazy {
        ImageItemAdapter { documentEntity, position -> itemOnClick(documentEntity, position) }
    }

    private val imageSearchViewModel by viewModels<ImageSearchViewModel> {
        ImageSearchViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageSearchBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
    }

    private fun initView() {
        binding.btnSearch.setOnClickListener {
            val string = binding.etSearch.text.toString()
            if (string.replace(" ", "") == "") {
                binding.etSearch.text.clear()
                Toast.makeText(requireActivity(), "검색어를 입력해주세요", Toast.LENGTH_SHORT).show()
            } else {
                imageSearchViewModel.getImageModelList(string)
                hideKeyboard()
            }
        }

        imageSearchViewModel.imageModel.observe(requireActivity()) {
            imageItemAdapter.itemList = it
            binding.rvImageList.adapter?.notifyDataSetChanged()
        }

        binding.rvImageList.apply {
            adapter = imageItemAdapter
            layoutManager = GridLayoutManager(requireActivity(), 2)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun itemOnClick(documentEntity: DocumentEntity, position: Int){
        documentEntity.isLike = !documentEntity.isLike
        if (documentEntity.isLike) MainActivity.PICKED_IMAGE.add(documentEntity)
        else MainActivity.PICKED_IMAGE.remove(documentEntity)
        imageItemAdapter.notifyItemChanged(position)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ImageSearchFragment().apply {
            }
    }
}
