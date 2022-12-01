package com.capstone.core.data.request

import com.google.firebase.database.Exclude

data class WaitingRoomRequest(
    val user_id: Int? = null,
    val name: String? = null,
    val image_url: String? = null,
    val online: Boolean? = true,
    val anonymous: Boolean? = false,
    val date: String? = null
) {

    @Exclude
    fun toMap(): Map<String, Any?> =
        mapOf(
            "user_id" to user_id,
            "name" to name,
            "image_url" to image_url,
            "online" to online,
            "anonymous" to anonymous,
            "date" to date
        )

    @Exclude
    fun updateMap(): Map<String, Any?> =
        mapOf(
            "online" to online
        )

}
