package com.example.tvshows.responses

import com.example.tvshows.models.TVShow
import com.google.gson.annotations.SerializedName

data class TVShowResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("pages")
    val pages: Int,
    @SerializedName("tv_shows")
    val tv_shows: List<TVShow>?

) {
    constructor(): this(0, 0, null)
}