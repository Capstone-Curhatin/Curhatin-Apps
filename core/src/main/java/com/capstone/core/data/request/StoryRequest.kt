package com.capstone.core.data.request

data class StoryRequest(
    val title: String,
    val body: String,
    val category_id: Int,
    val is_anonymous: Boolean
)