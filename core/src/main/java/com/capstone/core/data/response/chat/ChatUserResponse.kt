package com.capstone.core.data.response.chat

data class ChatUserResponse(
    val id: Int? = 0,
    val image_url: String? = "",
    val name: String? = "",
    val last_message: String? = "",
    val last_date: String? = "",
    val unread: Int? = 0,
    val anonymous: Boolean? = false
)