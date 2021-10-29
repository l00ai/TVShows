package com.example.tvshows.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.tvshows.repositories.TVShowDetailsRepository
import com.example.tvshows.responses.TVShowDetailsResponse

class TVShowDetailsViewModel: ViewModel() {

    private val tVShowDetailsRepository by lazy {
        TVShowDetailsRepository()
    }


    fun getTVShowsDetails(tvShowId: String): LiveData<TVShowDetailsResponse> {
        return tVShowDetailsRepository.getTVShowsDetails(tvShowId)
    }

}