package com.capstone.core.data.request.doctor

import com.google.firebase.database.Exclude

data class ChatRoomDoctorRequest(
    var id: String? = null,
    val sender_id: Int,
    val sender_name: String,
    val sender_image: String,
    val receiver_id: Int,
    val receiver_name: String,
    val receiver_image: String,
    val message: String,
    val read: Boolean? = false,
    val date: String,
    var unread: Int? = 0
) {

    @Exclude
    fun toMessageMap(): Map<String, Any?> =
        mapOf(
            "id" to id,
            "sender_id" to sender_id,
            "receiver_id" to receiver_id,
            "message" to message,
            "read" to read,
            "date" to date
        )

    @Exclude
    fun toSender(): Map<String, Any?> =
        mapOf(
            "id" to sender_id,
            "name" to sender_name,
            "image_url" to sender_image,
            "last_date" to date,
            "last_message" to message,
            "unread" to unread,
        )

    @Exclude
    fun toReceiver(): Map<String, Any?> =
        mapOf(
            "id" to receiver_id,
            "name" to receiver_name,
            "image_url" to receiver_image,
            "last_message" to message,
            "last_date" to date,
            "unread" to unread
        )
}