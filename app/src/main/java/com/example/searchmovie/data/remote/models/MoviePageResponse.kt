package com.example.searchmovie.data.remote.models

import com.google.gson.annotations.SerializedName

data class MoviePageResponse(
    val page: Int,
    val results: List<MovieResponse>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)
