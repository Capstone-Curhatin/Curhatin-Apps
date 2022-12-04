package com.capstone.core.ui.chat

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class ChatViewHolder <in T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: T)
}