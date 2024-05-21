package com.example.searchmovie.data.remote

import com.example.searchmovie.BuildConfig
import com.example.searchmovie.data.remote.models.ConfigResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ConfigApiService {
    @GET("configuration")
    suspend fun getConfiguration(@Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY): ConfigResponse
}
