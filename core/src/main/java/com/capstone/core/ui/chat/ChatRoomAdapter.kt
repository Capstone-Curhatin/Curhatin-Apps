package com.capstone.core.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.capstone.core.R
import com.capstone.core.data.response.chat.ChatRoomResponse
import com.capstone.core.utils.DateTimeUtil

class ChatRoomAdapter(private val id: Int) : RecyclerView.Adapter<ChatRoomAdapter.ViewHolder>() {

    companion object {
        private const val MESSAGE_TYPE_LEFT = 0
        private const val MESSAGE_TYPE_RIGHT = 1
    }

    private val differCallback = object : DiffUtil.ItemCallback<ChatRoomResponse>(){
        override fun areItemsTheSame(oldItem: ChatRoomResponse, newItem: ChatRoomResponse): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: ChatRoomResponse, newItem: ChatRoomResponse): Boolean =
            oldItem == newItem
    }

    private val listDiffer = AsyncListDiffer(this, differCallback)
    var setData: List<ChatRoomResponse>
        get() = listDiffer.currentList
        set(value) = listDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == MESSAGE_TYPE_LEFT){
            val binding = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_sender, parent, false)
            ViewHolder(binding)
        }else{
            val binding = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_receiver, parent, false)
            ViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chatList = setData[position]
        holder.message.text = chatList.message
        holder.date.text = DateTimeUtil.getDescriptiveMessageDateTime(chatList.date.toString(), true)
    }

    override fun getItemCount(): Int = setData.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val message: TextView = view.findViewById(R.id.tv_message)
        val date: TextView = view.findViewById(R.id.tv_date_chat)
    }

    override fun getItemViewType(position: Int): Int {
        return if (setData[position].sender_id == id){
            MESSAGE_TYPE_RIGHT
        }else{
            MESSAGE_TYPE_LEFT
        }
    }
}