package com.example.searchmovie.ui.moviedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import coil.load
import com.example.searchmovie.R
import com.example.searchmovie.databinding.FragmentMovieDeatilsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDeatilsBinding? = null
    private val binding get() = _binding!!


    private val args: MovieDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDeatilsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appBarConfiguration = AppBarConfiguration(
            findNavController().graph
        )
        binding.collapsingToolbar.setupWithNavController(
            binding.toolbar,
            findNavController(),
            appBarConfiguration
        )
        setupViews()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun setupViews() {
        binding.apply {
            // Not using binding adapter since we want different behavior for different image views
            movieImage.load(args.movie.backdropPath) {
                placeholder(R.drawable.loading)
                crossfade(true)
            }
            movieData = args.movie
        }
    }

}
