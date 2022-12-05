package com.capstone.core.data.request.notification

import com.google.firebase.database.Exclude

data class CreateNotificationRequest(
    var id: String? = null,
    val receiver_id: Int? = 0,
    val receiver_name: String? = null,
    val receiver_image: String? = null,
    val body: String? = null,
    val anonymous: Boolean? = false,
    val date: String? = null,
    val read: Boolean? = false,
    val unread: Int? = 0
){

    @Exclude
    fun toMap(): Map<String, Any?> =
        mapOf(
            "id" to id,
            "receiver_id" to receiver_id,
            "receiver_image" to receiver_image,
            "receiver_name" to receiver_name,
            "body" to body,
            "anonymous" to anonymous,
            "date" to date,
            "read" to read
        )

    @Exclude
    fun unreadToMap(): Map<String, Any?> =
        mapOf(
            "unread" to unread
        )

}
