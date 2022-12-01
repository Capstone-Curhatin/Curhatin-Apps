package com.capstone.core.domain.repository

import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.WaitingRoomRequest
import com.capstone.core.data.response.GenericResponse
import com.capstone.core.data.response.chat.WaitingRoomResponse
import kotlinx.coroutines.flow.Flow

interface WaitingRoomRepositoryImpl {

    fun createRoom(request: WaitingRoomRequest): Flow<Resource<GenericResponse>>
    fun updateStatus(request: WaitingRoomRequest): Flow<Resource<GenericResponse>>
    fun getPriority(id: Int): Flow<Resource<WaitingRoomResponse>>

}