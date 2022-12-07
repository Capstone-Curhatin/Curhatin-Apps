package com.capstone.core.domain.repository

import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.doctor.ChatRoomDoctorRequest
import kotlinx.coroutines.flow.Flow

interface ChatDoctorRepositoryImpl {

    fun sendMessage(request: ChatRoomDoctorRequest): Flow<Resource<Boolean>>

}