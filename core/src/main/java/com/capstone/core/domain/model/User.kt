package com.capstone.core.domain.model

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val email_verified_at: String? = null,
    val role: Int,
    val picture: String? = null,
    val is_premium: Boolean? = null,
    val premium_period: String? = null,
    val phone: String,
    val otp: Int,
    val fcm: String? = null,
    val profile_photo_url: String? = null,
    val doctor: Doctor? = null
)