package com.capstone.core.data.response.chat

data class WaitingRoomResponse(
    val user_id: Int? = null,
    val name: String? = null,
    val image_url: String? = null,
    val online: Boolean? = true,
    val date: String? = null
)