package com.example.tvshows.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tvshows.network.ApiClient
import com.example.tvshows.network.ApiService
import com.example.tvshows.responses.TVShowResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MostPopularTVShowRepository {

    private val apiService by lazy {
        ApiClient.getRetrofit().create(ApiService::class.java)
    }

    fun getMostPopularTVShows(page: Int): LiveData<TVShowResponse>{
        val data = MutableLiveData<TVShowResponse>()
        apiService.getMostPopularTVShows(page).enqueue(object :Callback<TVShowResponse>{
            override fun onResponse(call: Call<TVShowResponse>?, response: Response<TVShowResponse>?) {
                data.value = response!!.body()
            }

            override fun onFailure(call: Call<TVShowResponse>?, t: Throwable?) {
                data.value = null
            }

        })
        return data
    }

}