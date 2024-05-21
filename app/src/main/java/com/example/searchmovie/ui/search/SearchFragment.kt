package com.example.searchmovie.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.searchmovie.R
import com.example.searchmovie.databinding.FragmentSearchBinding
import com.example.searchmovie.ui.adapter.SearchMoviesPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModels()
    private lateinit var searchAdapter: SearchMoviesPagingAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchAdapter = SearchMoviesPagingAdapter(navController = findNavController())
        setupResultListener()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSearch()
    }

    private fun setupSearch() {
        binding.searchView.editText.doAfterTextChanged {
            viewModel.searchQuery.value = it.toString()
        }

        binding.searchRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)

            adapter = searchAdapter
        }


        searchAdapter.addLoadStateListener {
            if (it.refresh is LoadState.Loading) binding.progressBar.visibility =
                View.VISIBLE else binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupResultListener() {
        lifecycleScope.launch {
            viewModel.searchResult.collectLatest {
                searchAdapter.submitData(it)
            }
        }
    }
}
