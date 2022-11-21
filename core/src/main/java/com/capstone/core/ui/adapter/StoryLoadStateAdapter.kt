package com.capstone.core.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.capstone.core.databinding.StoryLoadStateBinding

class StoryLoadStateAdapter (private val retry: () -> Unit)
    : LoadStateAdapter<StoryLoadStateAdapter.ViewHolder>()
{

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        val binding =  StoryLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, retry)
    }

        inner class ViewHolder(private val binding: StoryLoadStateBinding, retry: () -> Unit) : RecyclerView.ViewHolder(binding.root){
            init {
                binding.btnRetry.setOnClickListener { retry.invoke() }
            }

            fun bind(loadState: LoadState) {
                if (loadState is LoadState.Error) binding.tvError.text =
                    loadState.error.localizedMessage
                binding.progress.isVisible = loadState is LoadState.Loading
                binding.tvError.isVisible = loadState is LoadState.Error
                binding.btnRetry.isVisible = loadState is LoadState.Error
            }
        }
}

