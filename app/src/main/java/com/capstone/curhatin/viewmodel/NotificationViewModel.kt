package com.capstone.curhatin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.notification.CreateNotificationRequest
import com.capstone.core.domain.usecase.notification.NotificationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(private val useCase: NotificationUseCase) : ViewModel() {

    fun createStory(request: CreateNotificationRequest) {
        viewModelScope.launch(Dispatchers.IO){
            useCase.createNotification(request).collectLatest {
                Timber.d("Send Notification: $it")
            }
        }
    }

}