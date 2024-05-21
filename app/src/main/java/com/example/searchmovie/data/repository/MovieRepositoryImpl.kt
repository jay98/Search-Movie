package com.example.searchmovie.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.searchmovie.data.remote.MovieApiService
import com.example.searchmovie.data.remote.models.MovieResponse
import com.example.searchmovie.data.remote.paging.SearchMoviesPagingSource
import com.example.searchmovie.data.remote.paging.TrendingMoviesPagingSource
import com.example.searchmovie.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class MovieRepositoryImpl(
    private val movieApiService: MovieApiService,
) : MovieRepository {
    override fun getTrendingMovies(): Flow<PagingData<MovieResponse>> = Pager(config = PagingConfig(
        pageSize = 20, enablePlaceholders = false
    ), pagingSourceFactory = { TrendingMoviesPagingSource(movieApiService) }).flow

    override fun searchMovies(query: String): Flow<PagingData<MovieResponse>> =
        Pager(config = PagingConfig(
            pageSize = 20, enablePlaceholders = false
        ), pagingSourceFactory = { SearchMoviesPagingSource(movieApiService, query) }).flow
}

