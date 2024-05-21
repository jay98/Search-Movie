package com.example.searchmovie.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.searchmovie.domain.usecase.SearchMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(searchMoviesUseCase: SearchMoviesUseCase) : ViewModel() {
    val searchQuery: MutableStateFlow<String> = MutableStateFlow(EMPTY_SEARCH_QUERY)


    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val searchResult = searchQuery.debounce(SEARCH_DELAY).flatMapLatest { searchMoviesUseCase(it) }
        .cachedIn(viewModelScope)

    companion object {
        private const val SEARCH_DELAY = 500L
        private const val EMPTY_SEARCH_QUERY = ""
    }
}
