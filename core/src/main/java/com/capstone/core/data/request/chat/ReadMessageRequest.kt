package com.capstone.core.data.request.chat

data class ReadMessageRequest(
    val sender_id: Int,
    val receive_id: Int
)