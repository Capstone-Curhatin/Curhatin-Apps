package com.capstone.curhatin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.capstone.core.data.common.Resource
import com.capstone.core.data.response.wrapper.WrapperList
import com.capstone.core.domain.model.Doctor
import com.capstone.core.domain.model.Story
import com.capstone.core.domain.model.User
import com.capstone.core.domain.usecase.doctor.DoctorUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

@HiltViewModel
class DoctorViewModel @Inject constructor(
    private val useCase: DoctorUseCase
) : ViewModel() {

    fun getDoctor(): LiveData<Resource<WrapperList<User>>> =
        useCase.getDoctor().asLiveData()

    fun detailDoctor(id: Int): LiveData<Resource<WrapperList<User>>> =
        useCase.detailDoctor(id).asLiveData()
}