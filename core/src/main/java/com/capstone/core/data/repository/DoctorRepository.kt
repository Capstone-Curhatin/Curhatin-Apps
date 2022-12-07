package com.capstone.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.capstone.core.data.common.Resource
import com.capstone.core.data.network.DoctorService
import com.capstone.core.data.response.wrapper.WrapperList
import com.capstone.core.data.source.DoctorDataSource
import com.capstone.core.data.source.StoryDataSource
import com.capstone.core.domain.model.Category
import com.capstone.core.domain.model.Doctor
import com.capstone.core.domain.model.User
import com.capstone.core.domain.repository.DoctorRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DoctorRepository @Inject constructor(
    private val data: DoctorDataSource,
    private val service: DoctorService
) : DoctorRepositoryImpl {

    override fun getDoctor(): Flow<Resource<WrapperList<User>>> =
        data.getDoctor()

}