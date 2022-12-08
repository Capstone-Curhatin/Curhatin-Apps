package com.capstone.core.data.source

import com.capstone.core.data.common.ErrorParser
import com.capstone.core.data.common.MyDispatchers
import com.capstone.core.data.common.Resource
import com.capstone.core.data.common.SafeCall
import com.capstone.core.data.network.DoctorService
import com.capstone.core.data.network.StoryService
import com.capstone.core.data.response.wrapper.Wrapper
import com.capstone.core.data.response.wrapper.WrapperList
import com.capstone.core.domain.model.Category
import com.capstone.core.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DoctorDataSource @Inject constructor(
    private val safeCall: SafeCall,
    private val dispatchers: MyDispatchers,
    private val errorParser: ErrorParser,
    private val service: DoctorService
) {

    fun getDoctor(): Flow<Resource<WrapperList<User>>> = flow {
        emit(Resource.Loading())

        val res = safeCall.enqueue(errorParser::converterGenericError, service::getDoctor)
        emit(res)
    }.flowOn(dispatchers.io)

    fun detailDoctor(id: Int): Flow<Resource<Wrapper<User>>> = flow {
        emit(Resource.Loading())

        val res = safeCall.enqueue(id, errorParser::converterGenericError, service::detailDoctor)
        emit(res)
    }.flowOn(dispatchers.io)
}