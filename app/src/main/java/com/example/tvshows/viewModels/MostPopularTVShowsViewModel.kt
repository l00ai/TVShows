package com.example.tvshows.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.tvshows.repositories.MostPopularTVShowRepository
import com.example.tvshows.responses.TVShowResponse

class MostPopularTVShowsViewModel: ViewModel() {

    private val mostPopularTVShowRepository by lazy {
        MostPopularTVShowRepository()
    }
    fun getMostPopularTVShows(page: Int): LiveData<TVShowResponse>{
        return mostPopularTVShowRepository.getMostPopularTVShows(page)
    }
}