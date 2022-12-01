package com.capstone.core.data.source.firebase

import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.chat.ChatRoomRequest
import com.capstone.core.data.request.chat.ChatUserRequest
import com.capstone.core.data.request.chat.ReadMessageRequest
import com.capstone.core.data.response.chat.ChatRoomResponse
import com.capstone.core.data.response.chat.ChatUserResponse
import kotlinx.coroutines.flow.Flow

interface ChatStorage {

    fun sendMessage(request: ChatRoomRequest): Flow<Resource<Boolean>>
    fun readMessage(request: ReadMessageRequest): Flow<Resource<List<ChatRoomResponse>>>
    fun createChatGroup(request: ChatUserRequest): Flow<Resource<Boolean>>
    fun getUserMessage(id: Int): Flow<Resource<List<ChatUserResponse>>>

}