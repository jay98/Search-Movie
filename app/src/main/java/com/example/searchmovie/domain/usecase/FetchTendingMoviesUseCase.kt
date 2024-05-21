package com.example.searchmovie.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.example.searchmovie.data.remote.models.toMovie
import com.example.searchmovie.domain.models.Movie
import com.example.searchmovie.domain.repository.MovieRepository
import com.example.searchmovie.util.ConfigManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FetchTendingMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val configManager: ConfigManager
) {
    operator fun invoke(): Flow<PagingData<Movie>> =
        movieRepository.getTrendingMovies().map {
            it.map { movieResponse ->
                movieResponse.toMovie(configManager.getConfig())
            }
        }
}
