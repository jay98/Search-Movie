package com.example.searchmovie.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.searchmovie.R
import com.example.searchmovie.databinding.MovieListItemBinding
import com.example.searchmovie.domain.models.Movie
import com.example.searchmovie.ui.trending.TrendingFragmentDirections

class MoviesPagingAdapter(val navController: NavController) :
    PagingDataAdapter<Movie, MoviesPagingAdapter.MovieViewHolder>(MovieDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieListItemBinding.inflate(inflater, parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        movie?.let {
            holder.bind(it)
        }
    }

    inner class MovieViewHolder(private val binding: MovieListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.apply {
                movieImage.load(movie.posterPath) {
                    placeholder(R.drawable.loading_small)
                }
                moviePoster.transitionName = movie.id.toString()
                moviePoster.setOnClickListener {
                    val directions: NavDirections =
                        TrendingFragmentDirections.actionNavigationTrendingToMovieDetailsFragment(
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
