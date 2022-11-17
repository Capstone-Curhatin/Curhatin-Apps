package com.capstone.core.domain.model

data class Story(
    val id: Int,
    val title: String,
    val body: String,
    val is_anonymous: Boolean,
    val comments: Int,
    val category: Category,
    val user: User
)