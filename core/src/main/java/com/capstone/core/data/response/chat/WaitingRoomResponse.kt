package com.capstone.core.data.response.chat

data class WaitingRoomResponse(
    val user_id: Int? = 0,
    val online: Boolean? = false,
    val date: String? = ""
)