package com.capstone.core.data.request.chat

data class SendNotificationRequest(
    val to: Int,
    val title: String,
    val body: String
)
