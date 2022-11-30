package com.capstone.core.data.source.firebase

import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.ChatRoomRequest
import com.capstone.core.data.response.GenericResponse
import kotlinx.coroutines.flow.Flow

interface ChatStorage {

    fun sendMessage(request: ChatRoomRequest): Flow<Resource<Boolean>>

}