package com.capstone.core.data.response.chat

data class WaitingRoomResponse(
    val status: Boolean,
    val data: WaitingRoomUser? = null
)

data class WaitingRoomUser(
    val user_id: Int? = -1,
    val name: String? = "",
    val image_url: String? = "",
    val online: Boolean? = true,
    val date: String? = "",
    val anonymous: Boolean? = false
)