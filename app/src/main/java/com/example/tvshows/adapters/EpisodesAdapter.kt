package com.example.tvshows.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tvshows.R
import com.example.tvshows.databinding.ItemContainerEpisodesBinding
import com.example.tvshows.models.Episode

class EpisodesAdapter(private var episodes: List<Episode>) :
    RecyclerView.Adapter<EpisodesAdapter.EpisodesViewHolder>() {


    inner class EpisodesViewHolder(val itemContainerEpisodesBinding: ItemContainerEpisodesBinding) :
        RecyclerView.ViewHolder(itemContainerEpisodesBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        EpisodesViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_container_episodes, parent, false)
        )

    override fun onBindViewHolder(holder: EpisodesViewHolder, position: Int) {
        bindEpisodes(episodes[position], holder.itemContainerEpisodesBinding)

    }

    override fun getItemCount() = episodes.size

    private fun bindEpisodes(episode: Episode, holder: ItemContainerEpisodesBinding) {
        var title = "S"
        var season = episode.season
        if (season.length == 1){
            season = "0$season"
        }
        var episodeNumber = episode.episode
        if (episodeNumber.length == 1){
            episodeNumber = "0$episodeNumber"
        }
        episodeNumber = "E$episodeNumber"
        title = "$title$season$episodeNumber"

        holder.title = title
        holder.name = episode.name
        holder.airDate = episode.airDate
    }
}