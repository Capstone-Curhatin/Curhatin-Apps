package com.capstone.core.data.request

import com.google.firebase.database.Exclude

data class ChatUserRequest(
    var id: String,
    val name: String,
    val image_url: String,
    val last_message: String,
    val last_date: String,
    val unread: Int
)