package com.capstone.core.data.response.wrapper

data class WrapperPaginateResponse<T>(
    val current_page: Int,
    val data: List<T>
)