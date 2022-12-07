package com.capstone.core.data.network

import com.capstone.core.data.response.wrapper.WrapperList
import com.capstone.core.domain.model.User
import com.capstone.core.utils.Endpoints
import retrofit2.Response
import retrofit2.http.GET

interface DoctorService {

    @GET(Endpoints.GET_DOCTOR)
    suspend fun getDoctor(): Response<WrapperList<User>>
}