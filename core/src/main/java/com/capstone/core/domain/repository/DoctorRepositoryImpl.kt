package com.capstone.core.domain.repository

import androidx.paging.PagingData
import com.capstone.core.data.common.Resource
import com.capstone.core.data.response.wrapper.Wrapper
import com.capstone.core.data.response.wrapper.WrapperList
import com.capstone.core.domain.model.Category
import com.capstone.core.domain.model.Doctor
import com.capstone.core.domain.model.Story
import com.capstone.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface DoctorRepositoryImpl {

    fun getDoctor(): Flow<Resource<WrapperList<User>>>
    fun detailDoctor(id: Int): Flow<Resource<Wrapper<User>>>
}