package com.capstone.core.data.response

data class ResponseWrapper<T>(
    val status: Boolean,
    val message: String,
    val data: T
)