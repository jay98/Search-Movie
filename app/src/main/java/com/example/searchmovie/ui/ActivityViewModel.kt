package com.example.searchmovie.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchmovie.domain.usecase.InitializeConfigUseCase
import com.example.searchmovie.util.ConfigManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(
    private val initializeConfigUseCase: InitializeConfigUseCase,
    configManager: ConfigManager
) : ViewModel() {

    var isReady = false

    init {
        configManager.getConfig()?.let {
            isReady = true
        }
        viewModelScope.launch {
            initializeConfigUseCase()
            isReady = true
        }
    }
}
