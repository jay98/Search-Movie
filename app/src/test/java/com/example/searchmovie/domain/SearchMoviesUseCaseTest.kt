package com.example.searchmovie.domain

import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import com.example.searchmovie.data.remote.models.MovieResponse
import com.example.searchmovie.domain.models.Config
import com.example.searchmovie.domain.models.Movie
import com.example.searchmovie.domain.repository.MovieRepository
import com.example.searchmovie.domain.usecase.SearchMoviesUseCase
import com.example.searchmovie.util.ConfigManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class SearchMoviesUseCaseTest {

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var configManager: ConfigManager

    private lateinit var searchMoviesUseCase: SearchMoviesUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        searchMoviesUseCase = SearchMoviesUseCase(movieRepository, configManager)
    }

    @Test
    fun `invoke should return searched movies with config applied`() = runTest {
        // Given
        val movieResponse = MovieResponse(
            id = 1,
            originalTitle = "Movie 1",
            overview = "Overview 1",
            posterPath = "/path1",
            backdropPath = "/backdrop1",
            voteAverage = 5.0,
            voteCount = 100,
            releaseDate = "2023-01-01"
        )
        val movie = Movie(
            id = 1,
            name = "Movie 1",
            backdropPath = "https://image.tmdb.org/t/p/backDropSize/backdrop1",
            posterPath = "https://image.tmdb.org/t/p/posterImageSize/path1",
            description = "Overview 1",
        )
        val pagingData = PagingData.from(listOf(movieResponse))

        `when`(configManager.getConfig()).thenReturn(
            Config(
                imageBaseUrl = "https://image.tmdb.org/t/p/",
                backDropSize = "w500",
                posterImageSize = "w342",
            )
        )
        `when`(movieRepository.searchMovies("test")).thenReturn(flowOf(pagingData))

        val result: Flow<PagingData<Movie>> = searchMoviesUseCase("test")
        val movies = result.asSnapshot()
        assertEquals(1, movies.size)
        assertEquals(movie.id, movies.first().id)
        assertEquals(movie.name, movies.first().name)


    }
}
