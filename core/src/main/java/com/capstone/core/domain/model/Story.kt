package com.capstone.core.domain.model

data class Story(
    val id: Int,
    val body: String,
    val is_anonymous: Boolean,
    val comments: Int,
    val category: Category,
    val user: User,
    val created_at: String
)