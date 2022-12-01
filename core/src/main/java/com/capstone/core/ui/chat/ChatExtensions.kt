package com.capstone.core.ui.chat

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.capstone.core.data.response.chat.ChatRoomResponse

class ChatItemUI (val data: ChatRoomResponse, val messageType: Int) {
    companion object {
        const val TYPE_SENDER = 0
        const val TYPE_RECEIVER = 1
    }
}

abstract class ChatViewHolder <in T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: T)
}