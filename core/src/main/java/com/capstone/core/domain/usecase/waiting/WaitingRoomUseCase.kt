package com.capstone.core.domain.usecase.waiting

import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.WaitingRoomRequest
import com.capstone.core.data.response.GenericResponse
import kotlinx.coroutines.flow.Flow

interface WaitingRoomUseCase {

    fun createRoom(request: WaitingRoomRequest): Flow<Resource<GenericResponse>>
    fun updateStatus(request: WaitingRoomRequest): Flow<Resource<GenericResponse>>
    fun getPriority(id: Int): Flow<Resource<Int>>

}