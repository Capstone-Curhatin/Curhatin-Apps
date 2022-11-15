package com.capstone.core.data.request.auth

data class VerifyOtpRequest(
    val email: String,
    val otp: Int
)
