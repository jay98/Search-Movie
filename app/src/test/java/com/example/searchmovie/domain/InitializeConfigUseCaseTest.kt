package com.example.searchmovie.domain

import com.example.searchmovie.domain.models.Config
import com.example.searchmovie.domain.repository.ConfigRepository
import com.example.searchmovie.domain.usecase.InitializeConfigUseCase
import com.example.searchmovie.util.ConfigManager
import com.example.searchmovie.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class InitializeConfigUseCaseTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    private val testDispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var configRepository: ConfigRepository

    @Mock
    private lateinit var configManager: ConfigManager

    private lateinit var initializeConfigUseCase: InitializeConfigUseCase

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        initializeConfigUseCase = InitializeConfigUseCase(configRepository, configManager)
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `invoke should set config when configRepository returns success`() = runTest {
        val configData = Config(
            imageBaseUrl = "https://image.tmdb.org/t/p/",
            backDropSize = "w500",
            posterImageSize = "w342",
        )
        val resource = Resource.Success(configData)

        whenever(configRepository.getConfig()).thenReturn(flow {
            emit(resource)
        }.flowOn(testDispatcher))

        initializeConfigUseCase.invoke()

        verify(configManager, times(1)).setConfig(configData)
    }

    @Test
    fun `invoke should not set config when configRepository returns error`() = runTest {
        val resource = Resource.Error<Config>("Error")

        whenever(configRepository.getConfig()).thenReturn(flow {
            emit(resource)
        }.flowOn(testDispatcher))

        initializeConfigUseCase.invoke()

        verify(configManager, times(0)).setConfig(any())
    }
}
