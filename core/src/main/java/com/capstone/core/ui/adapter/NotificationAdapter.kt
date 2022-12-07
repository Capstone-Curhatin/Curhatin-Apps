package com.capstone.core.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.capstone.core.data.response.NotificationResponse
import com.capstone.core.databinding.ItemNotificationBinding
import com.capstone.core.utils.Constant
import com.capstone.core.utils.DateTimeUtil
import com.capstone.core.utils.setImageUrl

class NotificationAdapter : RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<NotificationResponse>() {
        override fun areItemsTheSame(
            oldItem: NotificationResponse,
            newItem: NotificationResponse
        ): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(
            oldItem: NotificationResponse,
            newItem: NotificationResponse
        ): Boolean =
            oldItem == newItem
    }

    private val listDiffer = AsyncListDiffer(this, differCallback)
    var setData: List<NotificationResponse>
        get() = listDiffer.currentList
        set(value) = listDiffer.submitList(value)

    private var listener: ((NotificationResponse) -> Unit)? = null
    fun setOnItemClick(listener: ((NotificationResponse) -> Unit)?) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationAdapter.ViewHolder {
        val binding =
            ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationAdapter.ViewHolder, position: Int) {
        holder.bind(setData[position], listener)
    }

    override fun getItemCount(): Int = setData.size

    inner class ViewHolder(private val binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(data: NotificationResponse, listener: ((NotificationResponse) -> Unit)?) {
            with(binding) {

                if (data.anonymous == true) {
                    tvBody.text = "${Constant.ANONYMOUS} ${data.body}"
                    ivPicture.setImageUrl(Constant.ANONYMOUS_IMAGE)
                } else {
                    tvBody.text = "${data.receiver_name} ${data.body}"
                    ivPicture.setImageUrl(data.receiver_image.toString())
                }

                tvDate.text = DateTimeUtil.getDescriptiveMessageDateTime(data.date.toString(), true)
            }
        }
    }
}