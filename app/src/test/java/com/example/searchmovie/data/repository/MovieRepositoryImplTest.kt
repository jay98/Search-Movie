package com.example.searchmovie.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.testing.asSnapshot
import com.example.searchmovie.data.remote.MovieApiService
import com.example.searchmovie.data.remote.models.MoviePageResponse
import com.example.searchmovie.data.remote.models.MovieResponse
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock

class MovieRepositoryImplTest {

    private lateinit var movieApiService: MovieApiService
    private lateinit var movieRepository: MovieRepositoryImpl

    @Before
    fun setUp() {
        movieApiService = mock()
        movieRepository = MovieRepositoryImpl(movieApiService)
    }

    @Test
    fun `getTrendingMovies returns paging data`() = runTest {
        val movieResponseList = listOf(
            MovieResponse(
                id = 1,
                originalTitle = "Movie 1",
                overview = "Overview 1",
                posterPath = "/path1",
                backdropPath = "/backdrop1",
                voteAverage = 5.0,
                voteCount = 100,
                releaseDate = "2023-01-01"
            ),
            MovieResponse(
                id = 2,
                originalTitle = "Movie 2",
                overview = "Overview 2",
                posterPath = "/path1",
                backdropPath = "/backdrop1",
                voteAverage = 5.0,
                voteCount = 100,
                releaseDate = "2023-01-01"
            )
        )
        `when`(movieApiService.getTrendingMovies(1)).thenReturn(
            MoviePageResponse(
                results = movieResponseList,
                totalPages = 1,
                totalResults = 2,
                page = 1
            )
        )

        val result = movieRepository.getTrendingMovies()

        val snapshot = result.asSnapshot()
        assertEquals(2, snapshot.size)
        assertEquals(movieResponseList[0].originalTitle, snapshot[0].originalTitle)
        assertEquals(movieResponseList[1].originalTitle, snapshot[1].originalTitle)
    }

    @Test
    fun `searchMovies returns paging data`() = runTest {
        val query = "test"
        val movieResponseList = listOf(
            MovieResponse(
                id = 3,
                originalTitle = "Movie 1",
                overview = "Overview 1",
                posterPath = "/path1",
                backdropPath = "/backdrop1",
                voteAverage = 5.0,
                voteCount = 100,
                releaseDate = "2023-01-01"
            ),
            MovieResponse(
                id = 4,
                originalTitle = "Movie 2",
                overview = "Overview 2",
                posterPath = "/path1",
                backdropPath = "/backdrop1",
                voteAverage = 5.0,
                voteCount = 100,
                releaseDate = "2023-01-01"
            )
        )

        `when`(
            movieApiService.searchMovies(
                query,
                1
            )
        ).thenReturn(
            MoviePageResponse(
                results = movieResponseList,
                totalPages = 1,
                totalResults = 2,
                page = 1
            )
        )

        val result = movieRepository.searchMovies(query)

        val snapshot = result.asSnapshot()
        assertEquals(2, snapshot.size)
        assertEquals(movieResponseList[0].originalTitle, snapshot[0].originalTitle)
        assertEquals(movieResponseList[1].originalTitle, snapshot[1].originalTitle)
    }
}
