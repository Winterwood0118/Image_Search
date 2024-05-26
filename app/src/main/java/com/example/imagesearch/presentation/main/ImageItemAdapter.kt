package com.example.imagesearch.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagesearch.databinding.ThumbnailItemBinding
import com.example.imagesearch.presentation.entity.DocumentModel
import java.text.SimpleDateFormat

class ImageItemAdapter(
    private val onClick: (DocumentModel, Int) -> Unit
): RecyclerView.Adapter<ImageItemAdapter.ItemViewHolder>() {
    var itemList = listOf<DocumentModel>()
    class ItemViewHolder(
        private val binding: ThumbnailItemBinding,
        val onClick: (DocumentModel, Int) -> Unit
    ): RecyclerView.ViewHolder(binding.root){
        private val dateFormat1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        private val dateFormat2 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        private var currentItem: DocumentModel? = null
        private var currentPosition = -1

        init {
            itemView.setOnClickListener {
                currentItem?.let {
                    onClick(it, currentPosition)
                }
            }
        }

        fun bind(documentEntity: DocumentModel, position: Int){
            currentItem = documentEntity
            currentPosition = position
            with(binding){
                Glide.with(binding.root)
                    .load(documentEntity.thumbnailUrl)
                    .into(ivThumbnail)
                val date = dateFormat1.parse(documentEntity.dateTime)
                tvDate.text = date?.let { dateFormat2.format(it) } ?: "오류"
                tvSource.text = documentEntity.siteName
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ThumbnailItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding, onClick)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(itemList[position], position)
    }
}
