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
    val dateFormat = "yyyy-MM-dd HH:mm:ss"
    val simpleDateFormat = SimpleDateFormat(dateFormat)
    class ItemViewHolder(
        private val binding: ThumbnailItemBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(documentEntity: DocumentEntity){
            with(binding){
                Glide.with(binding.root)
                    .load(documentEntity.thumbnailUrl)
                    .into(ivThumbnail)
                //ivThumbnail.setImageURI(documentEntity.thumbnailUrl.toUri())
                tvDate.text = documentEntity.dateTime
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
