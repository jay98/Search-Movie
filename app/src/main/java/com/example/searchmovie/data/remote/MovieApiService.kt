package com.example.searchmovie.data.remote

import com.example.searchmovie.BuildConfig
import com.example.searchmovie.data.remote.models.MoviePageResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {

    // Assuming US audience hence keeping language only english
    @GET("trending/movie/day?language=en-US")
    suspend fun getTrendingMovies(
        @Query("page") page: Int, @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): MoviePageResponse

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): MoviePageResponse
}
