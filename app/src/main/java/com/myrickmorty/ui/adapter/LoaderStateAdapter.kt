package com.myrickmorty.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.myrickmorty.databinding.ErrorStateBinding

class LoaderStateAdapter constructor(private val retry: () -> Unit) :
    LoadStateAdapter<LoaderStateAdapter.LoaderStateViewHolder>() {


    override fun onBindViewHolder(holder: LoaderStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState,
    ): LoaderStateViewHolder {
        return LoaderStateViewHolder(ErrorStateBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false), retry)
    }

    class LoaderStateViewHolder(private val binding: ErrorStateBinding, retry: () -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnRetry.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.tvError.text = loadState.error.localizedMessage
            }
            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.btnRetry.isVisible = loadState !is LoadState.Loading
            binding.tvError.isVisible = loadState !is LoadState.Loading
        }
    }
}