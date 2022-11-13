package com.capstone.core.data.request.auth

data class RegisterRequest(
    val name: String,
    val email: String,
    val phone: String,
    val str_number: String? = null,
    val password: String,
    val role: Int
)
