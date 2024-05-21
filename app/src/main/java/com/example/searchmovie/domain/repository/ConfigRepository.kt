package com.example.searchmovie.domain.repository

import com.example.searchmovie.domain.models.Config
import com.example.searchmovie.util.Resource
import kotlinx.coroutines.flow.Flow

interface ConfigRepository {

    suspend fun getConfig(): Flow<Resource<Config>>
}
