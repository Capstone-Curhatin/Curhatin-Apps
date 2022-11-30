package com.capstone.core.data.request

import com.google.firebase.database.Exclude

data class ChatRoomRequest(
    var id: String,
    val sender_id: Int,
    val receiver_id: Int,
    val message: String,
    val read: Boolean? = false,
    val date: String
) {
    @Exclude
    fun toMap(): Map<String, Any?> =
        mapOf(
            "id" to id,
            "sender_id" to sender_id,
            "receiver_id" to receiver_id,
            "message" to message,
            "read" to read,
            "date" to date
        )
}