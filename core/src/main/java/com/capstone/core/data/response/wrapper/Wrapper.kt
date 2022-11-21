package com.capstone.core.data.response.wrapper

data class Wrapper<T>(
    val status: Boolean,
    val message: String,
    val data: T
)