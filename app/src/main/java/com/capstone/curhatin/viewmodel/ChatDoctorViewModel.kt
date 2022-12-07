package com.capstone.curhatin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.chat.ReadMessageRequest
import com.capstone.core.data.request.doctor.ChatRoomDoctorRequest
import com.capstone.core.data.response.chat.ChatRoomResponse
import com.capstone.core.data.response.chat.ChatUserResponse
import com.capstone.core.domain.usecase.chatDoctor.ChatDoctorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChatDoctorViewModel @Inject constructor(private val useCase: ChatDoctorUseCase) : ViewModel() {

    fun sendMessage(request: ChatRoomDoctorRequest): LiveData<Resource<Boolean>> =
        useCase.sendMessage(request).asLiveData()

    fun getUserMessage(id: String): LiveData<Resource<List<ChatUserResponse>>> =
        useCase.getUserMessage(id).asLiveData()

    fun readMessage(request: ReadMessageRequest): LiveData<Resource<List<ChatRoomResponse>>> =
        useCase.readMessage(request).asLiveData()
}