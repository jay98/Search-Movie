package com.example.searchmovie.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.searchmovie.databinding.SearchListItemBinding
import com.example.searchmovie.domain.models.Movie
import com.example.searchmovie.ui.search.SearchFragmentDirections

class SearchMoviesPagingAdapter(val navController: NavController) :
    PagingDataAdapter<Movie, SearchMoviesPagingAdapter.SearchResultViewHolder>(MovieDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SearchListItemBinding.inflate(inflater, parent, false)
        return SearchResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        val movie = getItem(position)
        movie?.let {
            holder.bind(it)
        }
    }

    inner class SearchResultViewHolder(private val binding: SearchListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.apply {
                movieData = movie
                // Not using binding adapter to load image since we want different bh
                movieImage.load(movie.posterPath) {
                    crossfade(true)
                }
                searchResult.setOnClickListener {
                    val directions: NavDirections =
                        SearchFragmentDirections.actionNavigationSearchToMovieDetailsFragment(
                            movie
                        )
                    navController.navigate(directions)
                }
            }
            binding.executePendingBindings()
        }
    }

    private class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.posterPath == newItem.posterPath
        }
    }
}
