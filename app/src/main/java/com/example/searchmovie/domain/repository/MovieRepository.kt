package com.example.searchmovie.domain.repository

import android.graphics.Movie
import androidx.paging.PagingData
import com.example.searchmovie.data.remote.models.MoviePageResponse
import com.example.searchmovie.data.remote.models.MovieResponse
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getTrendingMovies(): Flow<PagingData<MovieResponse>>

    fun searchMovies(query: String): Flow<PagingData<MovieResponse>>
}
