package com.capstone.core.data.response.wrapper

data class ResponseListWrapper<T>(
    val status: Boolean,
    val message: String,
    val data: List<T>
)