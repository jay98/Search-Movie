package com.example.searchmovie.domain.usecase

import com.example.searchmovie.domain.repository.ConfigRepository
import com.example.searchmovie.util.ConfigManager
import com.example.searchmovie.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class InitializeConfigUseCase @Inject constructor(
    private val configRepository: ConfigRepository,
    private val configManager: ConfigManager
) {
    suspend operator fun invoke() {
        configRepository.getConfig().flowOn(Dispatchers.IO).collectLatest {
            if (it is Resource.Success) {
                configManager.setConfig(it.data)
            }
        }
    }
}
