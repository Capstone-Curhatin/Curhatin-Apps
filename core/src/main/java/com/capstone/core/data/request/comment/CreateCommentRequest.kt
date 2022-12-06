package com.capstone.core.data.request.comment

import com.google.firebase.database.Exclude

data class CreateCommentRequest(
    var id: String? = null,
    val id_story: Int,
    val id_user: Int,
    val anonymous: Boolean,
    val image: String,
    val name: String,
    val body: String,
    val date: String
) {

    @Exclude
    fun toMap(): Map<String, Any?> = mapOf(
        "id" to id,
        "id_story" to id_story,
        "id_user" to id_user,
        "anonymous" to anonymous,
        "image" to image,
        "name" to name,
        "body" to body,
        "date" to date
    )

}