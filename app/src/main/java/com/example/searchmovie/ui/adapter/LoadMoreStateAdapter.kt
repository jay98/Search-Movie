package com.example.searchmovie.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.searchmovie.databinding.ItemLoadStateBinding

class LoadMoreStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<LoadMoreStateAdapter.LoadMoreStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadMoreStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, loadState: LoadState
    ): LoadMoreStateViewHolder {
        val binding =
            ItemLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadMoreStateViewHolder(binding, retry)
    }


    inner class LoadMoreStateViewHolder(
        private val binding: ItemLoadStateBinding, retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.refreshButton.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            binding.progressBar.visibility =
                if (loadState is LoadState.Loading) View.VISIBLE else View.GONE
            binding.refreshButton.visibility =
                if (loadState is LoadState.Error) View.VISIBLE else View.GONE
        }
    }
}
