package com.capstone.core.data.response.wrapper

data class WrapperPaginate<T>(
    val current_page: Int,
    val data: List<T>
)