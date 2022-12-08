package com.capstone.core.domain.model

data class Doctor(
    val id: Int,
    val user_id: Int,
    val str_number: String,
    val place_of_practice: String? = null,
    val is_verified: Boolean? = false,
    val reviews: Int? = null,
    val categories: List<String>? = null,
    val graduates: List<String>? = null,
    val specialist: String? = null,
    val experience: Int? = null,
)
