package com.capstone.core.data.response.wrapper

data class WrapperResponse<T>(
    val status: Boolean,
    val message: String,
    val data: T
)