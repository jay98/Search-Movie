package com.example.searchmovie.data.remote.models

import com.example.searchmovie.domain.models.Config
import com.example.searchmovie.domain.models.Movie
import com.google.gson.annotations.SerializedName

data class MovieResponse(
    val id: Int,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int
)


// Ideally these would be done in specific transformers and those transformers
// would be unit tested but to keep things simple going with this pattern
fun MovieResponse.toMovie(config: Config?): Movie = Movie(id = id,
    backdropPath = config?.let { config.imageBaseUrl + config.backDropSize + backdropPath }
        ?: backdropPath,
    posterPath = config?.let { config.imageBaseUrl + config.posterImageSize + posterPath }
        ?: posterPath,
    name = originalTitle,
    description = overview)
