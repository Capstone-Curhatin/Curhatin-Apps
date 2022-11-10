package com.capstone.core.data.base

import com.capstone.core.data.response.GenericResponse
import com.capstone.core.utils.Constant.TIMEOUT_ERROR
import com.capstone.core.utils.Constant.UNKNOWN_ERROR
import okhttp3.ResponseBody
import retrofit2.Response
import java.net.SocketTimeoutException

class SafeCall {

    suspend fun <T> enqueue(
        converter: (ResponseBody) -> GenericResponse?,
        call: suspend () -> Response<T>
    ): Resource<T> =
        try {
            val res = call()
            val body = res.body()
            val errorBody = res.errorBody()

            if (res.isSuccessful && body != null) {
                Resource.Success(body)
            } else if (errorBody != null) {
                val parsedError = converter(errorBody)
                if (parsedError != null){
                    Resource.Error(parsedError.message)
                }else{
                    Resource.Error(UNKNOWN_ERROR)
                }
            } else {
                Resource.Error(UNKNOWN_ERROR)
            }
        } catch (e: Exception) {
            when (e) {
                is SocketTimeoutException -> Resource.Error(TIMEOUT_ERROR)
                else -> Resource.Error(UNKNOWN_ERROR)
            }
        }

    suspend fun <T, R> enqueue(
        req: T,
        converter: (ResponseBody) -> GenericResponse?,
        call: suspend (T) -> Response<R>
    ): Resource<R> =
        try {
            val res = call(req)
            val body = res.body()
            val errorBody = res.errorBody()

            if (res.isSuccessful && body != null) {
                Resource.Success(body)
            } else if (errorBody != null) {
                val parsedError = converter(errorBody)
                if (parsedError != null){
                    Resource.Error(parsedError.message)
                }else{
                    Resource.Error(UNKNOWN_ERROR)
                }
            } else {
                Resource.Error(UNKNOWN_ERROR)
            }
        } catch (e: Exception) {
            when (e) {
                is SocketTimeoutException -> Resource.Error(TIMEOUT_ERROR)
                else -> Resource.Error(UNKNOWN_ERROR)
            }
        }

    suspend fun <T, U, R> enqueue(
        req1: T,
        req2: U,
        converter: (ResponseBody) -> GenericResponse?,
        call: suspend (T, U) -> Response<R>
    ): Resource<R> =
        try {
            val res = call(req1, req2)
            val body = res.body()
            val errorBody = res.errorBody()

            if (res.isSuccessful && body != null) {
                Resource.Success(body)
            } else if (errorBody != null) {
                val parsedError = converter(errorBody)
                if (parsedError != null){
                    Resource.Error(parsedError.message)
                }else{
                    Resource.Error(UNKNOWN_ERROR)
                }
            } else {
                Resource.Error(UNKNOWN_ERROR)
            }
        } catch (e: Exception) {
            when (e) {
                is SocketTimeoutException -> Resource.Error(TIMEOUT_ERROR)
                else -> Resource.Error(UNKNOWN_ERROR)
            }
        }

}