package com.capstone.core.data.source.firebase

import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.WaitingRoomRequest
import com.capstone.core.data.response.GenericResponse
import kotlinx.coroutines.flow.Flow

interface WaitingRoomStorage {

    fun createRoom(request: WaitingRoomRequest): Flow<Resource<GenericResponse>>
    fun updateStatus(request: WaitingRoomRequest): Flow<Resource<GenericResponse>>

}