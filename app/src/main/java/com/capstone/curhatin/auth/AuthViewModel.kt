package com.capstone.curhatin.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.core.data.common.MyDispatchers
import com.capstone.core.data.common.Resource
import com.capstone.core.data.request.auth.LoginRequest
import com.capstone.core.data.response.auth.LoginDataResponse
import com.capstone.core.domain.usecase.auth.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val useCase: AuthUseCase,
    private val dispatcher: MyDispatchers
) : ViewModel() {

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> get() = _loading

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> get() = _error

    private val _login: MutableLiveData<LoginDataResponse> = MutableLiveData()
    val login: LiveData<LoginDataResponse> get() = _login

    fun login(request: LoginRequest){
        viewModelScope.launch(dispatcher.io){
            useCase.login(request).collect { res ->
                when(res){
                    is Resource.Loading -> _loading.postValue(true)
                    is Resource.Error -> {
                        _loading.postValue(false)
                        _error.postValue(res.data?.message ?: res.message)
                    }
                    is Resource.Success -> {
                        _loading.postValue(false)
                        _login.postValue(res.data?.data)
                    }
                }
            }
        }
    }

}