package com.example.tvshows.listeners

import android.widget.ImageView
import com.example.tvshows.models.TVShow

interface TVShowListener {

    fun onTVShowClicked(tvShow: TVShow, image: ImageView)
}