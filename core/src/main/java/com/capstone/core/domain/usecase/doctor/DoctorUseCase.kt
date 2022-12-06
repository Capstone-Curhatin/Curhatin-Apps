package com.capstone.core.domain.usecase.doctor

import androidx.paging.PagingData
import com.capstone.core.data.common.Resource
import com.capstone.core.data.response.wrapper.WrapperList
import com.capstone.core.domain.model.Category
import com.capstone.core.domain.model.Doctor
import com.capstone.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface DoctorUseCase {

    fun getDoctor(): Flow<Resource<WrapperList<User>>>
}