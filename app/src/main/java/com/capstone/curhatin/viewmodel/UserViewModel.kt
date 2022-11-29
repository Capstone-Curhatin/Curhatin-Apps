package com.capstone.curhatin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.core.data.common.Resource
import com.capstone.core.data.response.wrapper.Wrapper
import com.capstone.core.domain.model.User
import com.capstone.core.domain.usecase.user.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val useCase: UserUseCase) : ViewModel() {

    fun fetch(): LiveData<Resource<Wrapper<User>>> =
        useCase.fetch().asLiveData()

}