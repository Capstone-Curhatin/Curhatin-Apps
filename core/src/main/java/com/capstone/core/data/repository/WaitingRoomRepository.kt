package com.capstone.core.data.repository

import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.WaitingRoomRequest
import com.capstone.core.data.response.GenericResponse
import com.capstone.core.data.source.firebase.WaitingRoomStorage
import com.capstone.core.domain.repository.WaitingRoomRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WaitingRoomRepository @Inject constructor(
    private val data: WaitingRoomStorage
) : WaitingRoomRepositoryImpl{

    override fun createRoom(request: WaitingRoomRequest): Flow<Resource<GenericResponse>> =
        data.createRoom(request)

    override fun updateStatus(request: WaitingRoomRequest): Flow<Resource<GenericResponse>> =
        data.updateStatus(request)

    override fun getPriority(id: Int): Flow<Resource<Int>> =
        data.getPriority(id)


}