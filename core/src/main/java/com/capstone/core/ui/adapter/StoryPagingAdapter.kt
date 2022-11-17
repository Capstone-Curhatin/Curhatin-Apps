package com.capstone.core.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.capstone.core.BuildConfig
import com.capstone.core.databinding.ItemStoryBinding
import com.capstone.core.domain.model.Story
import com.capstone.core.utils.DateTimeUtil
import com.capstone.core.utils.setImageUrl

class StoryPagingAdapter : PagingDataAdapter<Story, StoryPagingAdapter.ViewHolder>(differCallback) {

    companion object {
        val differCallback = object : DiffUtil.ItemCallback<Story>() {
            override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean =
                oldItem == newItem
        }
    }

    private var listener : ((Int) -> Unit)? = null
    fun setOnClickListener(listener: (Int) -> Unit){
        this.listener = listener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) holder.bind(currentItem, listener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(private val binding: ItemStoryBinding) : RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(data: Story, listener: ((Int) -> Unit)?){
            with(binding){

                tvBody.text = data.body
                tvCategory.text = "${data.category.name} • ${DateTimeUtil.getDescriptiveMessageDateTime(data.created_at, true)}"
                tvUserName.text = if (data.is_anonymous) "Anonymous" else data.user.name
                tvTotalComments.text = if (data.comments <= 0) "No comments" else "${data.comments} comments"

                if (!data.is_anonymous){
                    userPicture.setImageUrl(if(data.user.picture != null) "${BuildConfig.BASE_URL}/public/${data.user.picture}" else data.user.profile_photo_url )
                }

            }
        }
    }
}