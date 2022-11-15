package com.capstone.core.data.response.wrapper

data class ResponseWrapper<T>(
    val status: Boolean,
    val message: String,
    val data: T
)