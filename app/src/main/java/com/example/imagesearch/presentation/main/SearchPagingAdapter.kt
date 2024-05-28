package com.example.imagesearch.presentation.main

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagesearch.R
import com.example.imagesearch.databinding.ThumbnailItemBinding
import com.example.imagesearch.presentation.entity.DocumentModel
import com.example.imagesearch.presentation.main.SearchPagingAdapter.SearchPagingHolder.Companion.diffUtil
import java.text.SimpleDateFormat

class SearchPagingAdapter(private val onClick: (DocumentModel, Int) -> Unit) :
    PagingDataAdapter<DocumentModel, SearchPagingAdapter.SearchPagingHolder>(diffUtil) {

    class SearchPagingHolder(
        private val onClick: (DocumentModel, Int) -> Unit,
        private val binding: ThumbnailItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val dateFormat1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        private val dateFormat2 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        fun bind(documentEntity: DocumentModel) {
            with(binding) {
                Glide.with(binding.root)
                    .load(documentEntity.thumbnailUrl)
                    .into(ivThumbnail)
                val date = dateFormat1.parse(documentEntity.dateTime)
                tvDate.text = date?.let { dateFormat2.format(it) } ?: "오류"
                tvSource.text = documentEntity.siteName
            }
        }

        companion object {
            val diffUtil = object : DiffUtil.ItemCallback<DocumentModel>() {
                override fun areItemsTheSame(
                    oldItem: DocumentModel,
                    newItem: DocumentModel
                ): Boolean {
                    return oldItem.thumbnailUrl == newItem.thumbnailUrl
                }

                override fun areContentsTheSame(
                    oldItem: DocumentModel,
                    newItem: DocumentModel
                ): Boolean {
                    return oldItem == newItem
                }

            }
        }
    }

    override fun onBindViewHolder(holder: SearchPagingHolder, position: Int) {
        val item = getItem(position)?: DocumentModel("", "", "2023-08-09T00:09:38.000+09:00")
        Log.d("itemBinding", "${item.thumbnailUrl}")
        holder.bind(item)
    }



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchPagingHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.thumbnail_item, parent, false)

        return SearchPagingHolder(onClick, ThumbnailItemBinding.bind(view))
    }
}