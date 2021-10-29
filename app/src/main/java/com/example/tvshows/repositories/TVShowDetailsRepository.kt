package com.example.tvshows.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tvshows.network.ApiClient
import com.example.tvshows.network.ApiService
import com.example.tvshows.responses.TVShowDetailsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TVShowDetailsRepository {

    private val apiService by lazy {
        ApiClient.getRetrofit().create(ApiService::class.java)
    }

    fun getTVShowsDetails(tvShowId: String): LiveData<TVShowDetailsResponse> {
        val data = MutableLiveData<TVShowDetailsResponse>()
        apiService.getShowDetailsTVShows(tvShowId).enqueue(object : Callback<TVShowDetailsResponse> {
            override fun onResponse(call: Call<TVShowDetailsResponse>?, response: Response<TVShowDetailsResponse>?) {
                data.value = response!!.body()
            }

            override fun onFailure(call: Call<TVShowDetailsResponse>?, t: Throwable?) {
                data.value = null
            }
        })

        return data
    }
}