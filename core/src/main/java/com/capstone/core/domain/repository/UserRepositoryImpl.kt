package com.capstone.core.domain.repository

import com.capstone.core.data.common.Resource
import com.capstone.core.data.response.wrapper.Wrapper
import com.capstone.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepositoryImpl {
    fun fetch(): Flow<Resource<Wrapper<User>>>
}