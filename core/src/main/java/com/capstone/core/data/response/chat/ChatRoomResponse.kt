package com.capstone.core.data.response.chat

data class ChatRoomResponse(
    val id: String? = "",
    val message: String? = "",
    val date: String? = "",
    val read: Boolean? = false,
    val sender_id: Int? = 0,
    val receiver_id: Int? = 0,
    val anonymous: Boolean? = false
)