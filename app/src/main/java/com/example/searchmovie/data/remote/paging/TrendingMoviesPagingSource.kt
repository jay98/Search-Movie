package com.example.searchmovie.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.searchmovie.data.remote.MovieApiService
import com.example.searchmovie.data.remote.models.MovieResponse
import retrofit2.HttpException
import java.io.IOException
import javax.net.ssl.SSLHandshakeException

class TrendingMoviesPagingSource(private val movieApiService: MovieApiService) :
    PagingSource<Int, MovieResponse>() {
    override fun getRefreshKey(state: PagingState<Int, MovieResponse>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResponse> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = movieApiService.getTrendingMovies(page)
            LoadResult.Page(
                data = response.results,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (page >= response.totalPages) null else page + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        } catch (exception: SSLHandshakeException) {
            LoadResult.Error(exception)
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }

}
