package com.capstone.curhatin.viewmodel

import androidx.lifecycle.*
import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.chat.ChatRoomRequest
import com.capstone.core.data.request.chat.ChatUserRequest
import com.capstone.core.data.request.chat.ReadMessageRequest
import com.capstone.core.data.response.chat.ChatRoomResponse
import com.capstone.core.data.response.chat.ChatUserResponse
import com.capstone.core.domain.usecase.chat.ChatUseCase
import com.capstone.core.ui.chat.ChatItemUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(private val useCase: ChatUseCase) : ViewModel() {

    fun sendMessage(request: ChatRoomRequest): LiveData<Resource<Boolean>> =
        useCase.sendMessage(request).asLiveData()

    fun readMessage(request: ReadMessageRequest): LiveData<Resource<List<ChatRoomResponse>>> =
        useCase.readMessage(request).asLiveData()

    fun createChatGroup(request: ChatUserRequest): LiveData<Resource<Boolean>> =
        useCase.createChatGroup(request).asLiveData()

    fun getUserMessage(id: Int): LiveData<Resource<List<ChatUserResponse>>> =
        useCase.getUserMessage(id).asLiveData()

}