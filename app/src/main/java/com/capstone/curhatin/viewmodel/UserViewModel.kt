package com.capstone.curhatin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.StoryRequest
import com.capstone.core.data.request.chat.SendNotificationRequest
import com.capstone.core.data.request.update.UpdateRequest
import com.capstone.core.data.response.GenericResponse
import com.capstone.core.data.response.wrapper.Wrapper
import com.capstone.core.domain.model.User
import com.capstone.core.domain.usecase.user.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val useCase: UserUseCase) : ViewModel() {

    fun fetch(): LiveData<Resource<Wrapper<User>>> =
        useCase.fetch().asLiveData()

    fun sendNotification(request: SendNotificationRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.sendNotification(request).collectLatest {
                Timber.d("Send Notification: $it")
            }
        }
    }

    fun updateUser(request: UpdateRequest): LiveData<Resource<Wrapper<User>>> =
        useCase.updateUser(request).asLiveData()
}