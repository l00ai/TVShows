package com.example.tvshows.network

import com.example.tvshows.responses.TVShowDetailsResponse
import com.example.tvshows.responses.TVShowResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("most-popular")
    fun getMostPopularTVShows(@Query("page") page: Int): Call<TVShowResponse>

    @GET("show-details")
    fun getShowDetailsTVShows(@Query("q") tvShowId: String): Call<TVShowDetailsResponse>
}