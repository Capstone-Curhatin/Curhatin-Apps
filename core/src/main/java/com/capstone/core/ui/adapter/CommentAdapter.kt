package com.capstone.core.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.capstone.core.databinding.ItemCategoryBinding
import com.capstone.core.databinding.ItemCommentBinding
import com.capstone.core.domain.model.Category
import com.capstone.core.domain.model.Comment
import com.capstone.core.utils.Constant
import com.capstone.core.utils.DateTimeUtil
import com.capstone.core.utils.setImageUrl

class CommentAdapter : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Comment>() {
        override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean =
            oldItem == newItem
    }

    private val listDiffer = AsyncListDiffer(this, differCallback)
    var setData: List<Comment>
        get() = listDiffer.currentList
        set(value) = listDiffer.submitList(value)

    private var listener: ((Comment) -> Unit)? = null
    fun setOnItemClick(listener: ((Comment) -> Unit)?) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentAdapter.ViewHolder {
        val binding =
            ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentAdapter.ViewHolder, position: Int) {
        holder.bind(setData[position], listener)
    }

    override fun getItemCount(): Int = setData.size

    inner class ViewHolder(private val binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Comment, listener: ((Comment) -> Unit)?) {
            with(binding) {

                if (data.anonymous == true){
                    tvName.text = Constant.ANONYMOUS
                    ivPicture.setImageUrl(Constant.ANONYMOUS_IMAGE)
                }else{
                    tvName.text = data.name
                    ivPicture.setImageUrl(data.image.toString())
                }

                tvDate.text = DateTimeUtil.getDescriptiveMessageDateTime(data.date.toString(), true)
                tvBody.text = data.body
            }
        }
    }
}