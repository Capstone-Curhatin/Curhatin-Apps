package com.capstone.core.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.capstone.core.databinding.ItemCategoryBinding
import com.capstone.core.domain.model.Category

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean =
            oldItem == newItem
    }

    private val listDiffer = AsyncListDiffer(this, differCallback)
    var setData: List<Category>
        get() = listDiffer.currentList
        set(value) = listDiffer.submitList(value)

    private var listener: ((Category) -> Unit)? = null
    fun setOnItemClick(listener: ((Category) -> Unit)?) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
        holder.bind(setData[position], listener)
    }

    override fun getItemCount(): Int = setData.size

    inner class ViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Category, listener: ((Category) -> Unit)?) {
            with(binding) {
                tvCategory.text = data.name


            }
        }
    }
}