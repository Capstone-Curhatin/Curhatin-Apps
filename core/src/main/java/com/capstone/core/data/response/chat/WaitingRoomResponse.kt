package com.capstone.core.data.response.chat

data class WaitingRoomResponse(
    val user_id: Int? = -1,
    val name: String? = "",
    val image_url: String? = "",
    val online: Boolean? = true,
    val date: String? = ""
)