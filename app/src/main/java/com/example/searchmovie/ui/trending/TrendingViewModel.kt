package com.example.searchmovie.ui.trending

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.searchmovie.domain.usecase.FetchTendingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TrendingViewModel @Inject constructor(fetchTendingMoviesUseCase: FetchTendingMoviesUseCase) :
    ViewModel() {
    val trendingMovies = fetchTendingMoviesUseCase().cachedIn(viewModelScope)
}
