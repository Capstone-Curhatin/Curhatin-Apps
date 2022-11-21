package com.capstone.core.data.response.wrapper

data class WrapperList<T>(
    val status: Boolean,
    val message: String,
    val data: List<T>
)