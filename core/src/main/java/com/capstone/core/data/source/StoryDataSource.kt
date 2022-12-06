package com.capstone.core.data.source

import com.capstone.core.data.common.ErrorParser
import com.capstone.core.data.common.MyDispatchers
import com.capstone.core.data.common.Resource
import com.capstone.core.data.common.SafeCall
import com.capstone.core.data.network.StoryService
import com.capstone.core.data.request.StoryRequest
import com.capstone.core.data.response.GenericResponse
import com.capstone.core.data.response.wrapper.WrapperList
import com.capstone.core.domain.model.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class StoryDataSource @Inject constructor(
    private val safeCall: SafeCall,
    private val dispatchers: MyDispatchers,
    private val errorParser: ErrorParser,
    private val service: StoryService
) {

    fun createStory(request: StoryRequest): Flow<Resource<GenericResponse>> = flow {
        emit(Resource.Loading())

        val res = safeCall.enqueue(request, errorParser::converterGenericError, service::createStory)
        emit(res)
    }.flowOn(dispatchers.io)

    fun getCategory(): Flow<Resource<WrapperList<Category>>> = flow {
        emit(Resource.Loading())

        val res = safeCall.enqueue(errorParser::converterGenericError, service::getCategory)
        emit(res)
    }.flowOn(dispatchers.io)

    fun increment(id: Int): Flow<Resource<Any>> = flow {
        emit(Resource.Loading())

        val res = safeCall.enqueue(id, errorParser::converterGenericError, service::increment)
        emit(res)
    }.flowOn(dispatchers.io)
}