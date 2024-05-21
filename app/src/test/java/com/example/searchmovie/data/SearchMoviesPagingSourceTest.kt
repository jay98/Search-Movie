package com.example.searchmovie.data

import androidx.paging.PagingSource
import com.example.searchmovie.data.remote.MovieApiService
import com.example.searchmovie.data.remote.models.MoviePageResponse
import com.example.searchmovie.data.remote.models.MovieResponse
import com.example.searchmovie.data.remote.paging.SearchMoviesPagingSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.doAnswer
import retrofit2.HttpException
import java.io.IOException
import javax.net.ssl.SSLHandshakeException

class SearchMoviesPagingSourceTest {

    private lateinit var movieApiService: MovieApiService
    private lateinit var pagingSource: SearchMoviesPagingSource

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        movieApiService = Mockito.mock(MovieApiService::class.java)
        pagingSource = SearchMoviesPagingSource(movieApiService, "query")
    }

    @Test
    fun `load returns Page on successful load`() = runTest {
        val movieResponses = listOf(
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
        val movieSearchResponse =
            MoviePageResponse(results = movieResponses, totalPages = 1, totalResults = 2, page = 1)

        Mockito.`when`(movieApiService.searchMovies("query", 1)).thenReturn(movieSearchResponse)

        val expectedResult = PagingSource.LoadResult.Page(
            data = movieResponses,
            prevKey = null,
            nextKey = null
        )

        val actualResult = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )

        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun `load returns Error on IOException`() = runTest {
        Mockito.`when`(movieApiService.searchMovies("query", 1)).doAnswer {
            throw IOException()
        }

        val actualResult = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )

        assertTrue(actualResult is PagingSource.LoadResult.Error)
        assertTrue((actualResult as PagingSource.LoadResult.Error).throwable is IOException)
    }

    @Test
    fun `load returns Error on SSLHandshakeException`() = runTest {
        Mockito.`when`(movieApiService.searchMovies("query", 1)) doAnswer {
            throw SSLHandshakeException("Failed to connect")
        }

        val actualResult = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )

        assertTrue(actualResult is PagingSource.LoadResult.Error)
        assertTrue((actualResult as PagingSource.LoadResult.Error).throwable is SSLHandshakeException)
    }


    @Test
    fun `load returns Error on HttpException`() = runTest {
        Mockito.`when`(movieApiService.searchMovies("query", 1))
            .thenThrow(HttpException::class.java)

        val actualResult = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )

        assertTrue(actualResult is PagingSource.LoadResult.Error)
        assertTrue((actualResult as PagingSource.LoadResult.Error).throwable is HttpException)
    }

}

