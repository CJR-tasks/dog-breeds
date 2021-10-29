package com.curtjrees.dogbreeds.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.curtjrees.dogbreeds.databinding.ItemImageBinding
import java.util.concurrent.Executors

class ImagesListAdapter : ListAdapter<String, ImagesListAdapter.ImageViewHolder>(
    AsyncDifferConfig.Builder(diffCallback).setBackgroundThreadExecutor(Executors.newSingleThreadExecutor()).build()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder = ImageViewHolder.create(parent)

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ImageViewHolder(private val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imageUrl: String) {
            binding.imageView.load(imageUrl)
        }

        companion object {
            fun create(parent: ViewGroup) = ImageViewHolder(ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    private companion object {
        val diffCallback = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    }

}