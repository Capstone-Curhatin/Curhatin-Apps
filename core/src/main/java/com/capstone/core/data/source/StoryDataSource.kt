package com.capstone.core.data.source

import com.capstone.core.data.common.ErrorParser
import com.capstone.core.data.common.MyDispatchers
import com.capstone.core.data.common.Resource
import com.capstone.core.data.common.SafeCall
import com.capstone.core.data.network.StoryService
import com.capstone.core.data.request.StoryRequest
import com.capstone.core.data.response.GenericResponse
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

}