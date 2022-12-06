package com.capstone.core.data.response

data class NotificationResponse(
    val anonymous: Boolean? = false,
    val date: String? = "",
    val receiver_id: Int? = 0,
    val receiver_image: String? = "",
    val receiver_name: String? = "",
    val body: String? = "",
)