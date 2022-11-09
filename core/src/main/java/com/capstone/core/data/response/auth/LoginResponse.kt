package com.capstone.core.data.response.auth

import com.capstone.core.di.model.User

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val data: User
)

data class LoginDataResponse(
    val access_token: String,
    val token_type: String
)
