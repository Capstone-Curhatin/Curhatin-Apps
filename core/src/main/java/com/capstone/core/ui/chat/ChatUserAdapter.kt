package com.capstone.core.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.capstone.core.data.response.chat.ChatUserResponse
import com.capstone.core.databinding.ItemChatUserBinding
import com.capstone.core.utils.Constant
import com.capstone.core.utils.DateTimeUtil
import com.capstone.core.utils.setImageUrl

class ChatUserAdapter : RecyclerView.Adapter<ChatUserAdapter.ViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<ChatUserResponse>(){
        override fun areItemsTheSame(oldItem: ChatUserResponse, newItem: ChatUserResponse): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: ChatUserResponse, newItem: ChatUserResponse): Boolean =
            oldItem == newItem
    }

    private val listDiffer = AsyncListDiffer(this, differCallback)
    var setData: List<ChatUserResponse>
        get() = listDiffer.currentList
        set(value) = listDiffer.submitList(value)

    private var listener: ((ChatUserResponse) -> Unit)? = null
    fun setOnItemClick(listener: ((ChatUserResponse) -> Unit)?){
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemChatUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.bind(setData[position], listener)
    }

    override fun getItemCount(): Int = setData.size

    inner class ViewHolder(private val binding: ItemChatUserBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data: ChatUserResponse, listener: ((ChatUserResponse) -> Unit)?){
            with(binding){

                // check anonymous chat
                if (data.anonymous == true){
                    tvName.text = Constant.ANONYMOUS
                    ivPicture.setImageUrl(Constant.ANONYMOUS_IMAGE)
                }else{
                    tvName.text = data.name
                    ivPicture.setImageUrl(data.image_url.toString())
                }

                // check unread message
                if (data.unread != 0){
                    tvUnreadMessages.visibility = View.VISIBLE
                    tvUnreadMessages.text = if (data.unread!! > 99) "99" else data.unread.toString()
                }else{
                    tvUnreadMessages.visibility = View.GONE
                }

                tvLastMessage.text = data.last_message
                tvLastDate.text = DateTimeUtil.getDescriptiveMessageDateTime(data.last_date.toString(), true)

                rootView.setOnClickListener {
                    listener?.let { listener(data) }
                }

            }
        }
    }

}
