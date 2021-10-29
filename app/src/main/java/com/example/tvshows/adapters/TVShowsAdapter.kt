package com.example.tvshows.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tvshows.R
import com.example.tvshows.databinding.ItemContainerBinding
import com.example.tvshows.listeners.TVShowListener
import com.example.tvshows.models.Episode
import com.example.tvshows.models.TVShow

class TVShowsAdapter(private val tvShows: List<TVShow>, private val tVShowListener: TVShowListener) :
    RecyclerView.Adapter<TVShowsAdapter.TVShowsViewHolder>() {

    inner class TVShowsViewHolder(val itemContainerTVShowBinding: ItemContainerBinding) :
        RecyclerView.ViewHolder(itemContainerTVShowBinding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        TVShowsViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
            R.layout.item_container, parent, false)
        )


    override fun onBindViewHolder(holder: TVShowsViewHolder, position: Int) {
        holder.itemContainerTVShowBinding.tvShow = tvShows[position]
        holder.itemContainerTVShowBinding.executePendingBindings()
        holder.itemContainerTVShowBinding.root.setOnClickListener {
            tVShowListener.onTVShowClicked(tvShows[position], holder.itemContainerTVShowBinding.imageTvShow)
        }

    }

    override fun getItemCount() = tvShows.size


}