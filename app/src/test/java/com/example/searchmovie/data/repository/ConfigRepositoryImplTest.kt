package com.example.searchmovie.data.repository

import com.example.searchmovie.data.remote.ConfigApiService
import com.example.searchmovie.data.remote.models.ConfigResponse
import com.example.searchmovie.data.remote.models.ImageConfigResponse
import com.example.searchmovie.data.remote.models.toConfig
import com.example.searchmovie.util.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import retrofit2.HttpException
import java.io.IOException
import javax.net.ssl.SSLHandshakeException

@ExperimentalCoroutinesApi
class ConfigRepositoryImplTest {

    private lateinit var apiService: ConfigApiService
    private lateinit var configRepository: ConfigRepositoryImpl

    @Before
    fun setUp() {
        apiService = mock()
        configRepository = ConfigRepositoryImpl(apiService)
    }

    @Test
    fun `getConfig returns success`() = runTest {
        val configResponse = ConfigResponse(
            ImageConfigResponse(
                baseUrl = "https://image.tmdb.org/t/p/",
                secureBaseUrl = "https://image.tmdb.org/t/p/",
                backdropSizes = listOf("w300", "w780", "w1280", "original"),
                logoSizes = listOf("w45", "w92", "w154", "w185", "w300", "w500", "original"),
                posterSizes = listOf("w92", "w154", "w185", "w342", "w500", "w780", "original"),
                profileSizes = listOf("w45", "w185", "h632", "original"),
                stillSizes = listOf("w92", "w185", "w300", "original")
            )
        ) // Create a dummy Config object
        `when`(apiService.getConfiguration()).thenReturn(configResponse)

        val result = configRepository.getConfig().toList()

        assertEquals(1, result.size)
        assertTrue(result[0] is Resource.Success)
        assertEquals(configResponse.toConfig(), (result[0] as Resource.Success).data)
    }

    @Test
    fun `getConfig returns error on IOException`() = runTest {
        `when`(apiService.getConfiguration()).doAnswer { throw  IOException("Network error") }

        val result = configRepository.getConfig().toList()

        assertEquals(1, result.size)
        assertTrue(result[0] is Resource.Error)
        assertEquals("Network error", (result[0] as Resource.Error).message)
    }

    @Test
    fun `getConfig returns error on HttpException`() = runTest {
        val exception = mock<HttpException>()
        `when`(apiService.getConfiguration()).thenThrow(exception)
        `when`(exception.message).thenReturn("HTTP error")

        val result = configRepository.getConfig().toList()

        assertEquals(1, result.size)
        assertTrue(result[0] is Resource.Error)
        assertEquals("HTTP error", (result[0] as Resource.Error).message)
    }

    @Test
    fun `getConfig returns error on SSLHandshakeException`() = runTest {
        `when`(apiService.getConfiguration()).doAnswer { throw SSLHandshakeException("SSL error") }

        val result = configRepository.getConfig().toList()

        assertEquals(1, result.size)
        assertTrue(result[0] is Resource.Error)
        assertEquals("SSL error", (result[0] as Resource.Error).message)
    }
}
