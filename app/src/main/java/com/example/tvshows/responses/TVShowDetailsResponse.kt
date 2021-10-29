package com.example.tvshows.responses

import com.example.tvshows.models.TVShowDetails
import com.google.gson.annotations.SerializedName

class TVShowDetailsResponse(
    @SerializedName("tvShow")
    val tvShowDetails: TVShowDetails
)