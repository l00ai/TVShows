package com.example.tvshows.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tvshows.R
import com.example.tvshows.databinding.ItemContainerSliderImageBinding

class ImageSliderAdapter(private val sliderImages: List<String>): RecyclerView.Adapter<ImageSliderAdapter.ImageSliderViewHolder>() {

    inner class ImageSliderViewHolder(private val itemContainerSliderImageBinding: ItemContainerSliderImageBinding)
        : RecyclerView.ViewHolder(itemContainerSliderImageBinding.root){

         fun bindSliderImage(imageUrl: String){
            itemContainerSliderImageBinding.imageUrl = imageUrl
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ImageSliderViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_container_slider_image , parent, false)
    )

    override fun onBindViewHolder(holder: ImageSliderViewHolder, position: Int) {
        holder.bindSliderImage(sliderImages[position])
    }

    override fun getItemCount() = sliderImages.size
}