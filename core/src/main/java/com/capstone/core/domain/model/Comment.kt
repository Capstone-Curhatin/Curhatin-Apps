package com.capstone.core.domain.model

data class Comment(
    val id: String? = "",
    val body: String? = "",
    val anonymous: Boolean? = false,
    val id_story: Int? = 0,
    val id_user: Int? = 0,
    val image: String? = "",
    val name: String? = "",
    val date: String? = ""
)