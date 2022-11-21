package com.capstone.core.data.request

data class StoryRequest(
    val body: String,
    val category_id: Int? = null,
    val is_anonymous: Boolean
)