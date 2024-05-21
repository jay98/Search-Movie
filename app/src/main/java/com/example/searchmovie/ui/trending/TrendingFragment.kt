package com.example.searchmovie.ui.trending

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import com.example.searchmovie.R
import com.example.searchmovie.databinding.FragmentTrendingBinding
import com.example.searchmovie.ui.adapter.LoadMoreStateAdapter
import com.example.searchmovie.ui.adapter.MoviesPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TrendingFragment : Fragment() {

    private var _binding: FragmentTrendingBinding? = null
    private val binding get() = _binding!!

    private val trendingViewModel: TrendingViewModel by viewModels()

    private lateinit var trendingAdapter: MoviesPagingAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        trendingAdapter = MoviesPagingAdapter(findNavController())
        setupTrendingPaging()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrendingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()
        setUpRefresh()

        // load function doesn't directly display the gifs properly but the error does so using
        // this as a work around. Ideally we would add using custom image loader with gif decoder
        // but for the sake of this project just doing it this way
        binding.movieImage.load(INVALID_URL) {
            error(R.drawable.treanding_background)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecycleView() {

        binding.tendingRecyclerView.apply {
            layoutManager = GridLayoutManager(activity, TRENDING_SPAN_COUNT).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return if (trendingAdapter.itemCount == position) {
                            TRENDING_SPAN_COUNT
                        } else {
                            TRENDING_LOADER_SPAN_COUNT
                        }
                    }
                }
            }
            adapter =
                trendingAdapter.withLoadStateHeaderAndFooter(footer = LoadMoreStateAdapter { trendingAdapter.retry() },
                    header = LoadMoreStateAdapter { trendingAdapter.retry() })

            trendingAdapter.addLoadStateListener { loadState ->
                binding.swipeRefreshLayout.isRefreshing = loadState.refresh is LoadState.Loading
                binding.refreshButton.visibility = if (loadState.refresh is LoadState.Error) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }

        }
    }

    private fun setupTrendingPaging() {
        lifecycleScope.launch {
            trendingViewModel.trendingMovies.collectLatest {
                trendingAdapter.submitData(it)
            }
        }
    }

    private fun setUpRefresh() {
        setUpSwipeRefresh()
        binding.refreshButton.setOnClickListener {
            trendingAdapter.refresh()
        }
    }

    private fun setUpSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            trendingAdapter.refresh()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    companion object {
        private const val TRENDING_SPAN_COUNT = 3
        private const val TRENDING_LOADER_SPAN_COUNT = 1
        private const val INVALID_URL = "invalid_url"
    }
}
