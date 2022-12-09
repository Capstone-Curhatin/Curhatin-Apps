package com.capstone.core.data.request.chat

import com.capstone.core.utils.Constant
import com.google.firebase.database.Exclude

data class ChatUserRequest(
    val sender_id: Int? = 0,
    val receiver_id: Int? = 0,
    val sender_name: String? = "",
    val receiver_name: String? = "",
    val sender_image_url: String? = "",
    val receiver_image_url: String? = "",
    val last_message: String? = "",
    val last_date: String? = null,
    val anonymous_sender: Boolean? = false,
    val anonymous_receiver: Boolean? = false,
    val unread: Int? = 0
){
    @Exclude
    fun toReceiverMap(): Map<String, Any?> =
        mapOf(
            "id" to receiver_id,
            "name" to if (anonymous_receiver == true) Constant.ANONYMOUS else receiver_name,
            "image_url" to receiver_image_url,
            "last_message" to last_message,
            "last_date" to last_date,
            "unread" to unread,
            "anonymous" to anonymous_receiver
        )

    @Exclude
    fun toSenderMap(): Map<String, Any?> =
        mapOf(
            "id" to sender_id,
            "name" to if (anonymous_sender == true) Constant.ANONYMOUS else sender_name,
            "image_url" to sender_image_url,
            "last_message" to last_message,
            "last_date" to last_date,
            "unread" to unread,
            "anonymous" to anonymous_sender
        )

    @Exclude
    fun toUpdateLastMessage(): Map<String, Any?> =
        mapOf(
            "last_date" to last_date,
            "last_message" to last_message,
            "unread" to unread,
        )

}