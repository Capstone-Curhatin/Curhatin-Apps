package com.capstone.core.domain.usecase.waiting

import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.WaitingRoomRequest
import com.capstone.core.data.response.GenericResponse
import com.capstone.core.domain.repository.WaitingRoomRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WaitingRoomInteractor @Inject constructor(
    private val repo: WaitingRoomRepositoryImpl
) : WaitingRoomUseCase {

    override fun createRoom(request: WaitingRoomRequest): Flow<Resource<GenericResponse>> =
        repo.createRoom(request)

    override fun updateStatus(request: WaitingRoomRequest): Flow<Resource<GenericResponse>> =
        repo.updateStatus(request)

    override fun getPriority(id: Int): Flow<Resource<Int>> =
        repo.getPriority(id)

}