package com.example.imagesearch.presentation.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagesearch.databinding.ActivityMainBinding
import com.example.imagesearch.databinding.ThumbnailItemBinding
import com.example.imagesearch.presentation.entity.DocumentEntity
import java.text.SimpleDateFormat

class ImageItemAdapter(
): RecyclerView.Adapter<ImageItemAdapter.ItemViewHolder>() {
    var itemList = listOf<DocumentEntity>()
    class ItemViewHolder(
        private val binding: ThumbnailItemBinding
    ): RecyclerView.ViewHolder(binding.root){
        private val dateFormat1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        private val dateFormat2 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        fun bind(documentEntity: DocumentEntity){
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
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(itemList[position])
    }
}
