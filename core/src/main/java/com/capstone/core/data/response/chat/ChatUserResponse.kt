package com.capstone.core.data.response.chat

data class ChatUserResponse(
    val id: Int? = null,
    val image_url: String? = null,
    val name: String? = null,
    val last_message: String? = null,
    val last_date: String? = null,
    val unread: Int? = null
)