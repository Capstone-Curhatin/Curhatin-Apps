package com.capstone.curhatin.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.core.data.MyDispatchers
import com.capstone.core.data.Resource
import com.capstone.core.data.request.auth.LoginRequest
import com.capstone.core.domain.model.User
import com.capstone.core.domain.usecase.auth.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val useCase: AuthUseCase,
    private val dispatchers: MyDispatchers
) : ViewModel() {

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> get() = _loading

    private val _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> get() = _error

    private val _user: MutableLiveData<User> = MutableLiveData()
    val user: LiveData<User> get() = _user

    fun login(request: LoginRequest){
        viewModelScope.launch(dispatchers.io) {
            useCase.login(request).collect { res ->
                when(res){
                    is Resource.Loading -> _loading.postValue(true)
                    is Resource.Error -> {
                        _loading.postValue(false)
                        _error.postValue(res.e)
                    }
                    is Resource.Success -> {
                        _loading.postValue(false)
                        _user.postValue(res.data.data)
                    }
                }
            }
        }
    }
}