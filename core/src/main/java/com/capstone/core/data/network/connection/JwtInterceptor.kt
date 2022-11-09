package com.capstone.core.data.network.connection

import com.capstone.core.utils.MySharedPreference
import okhttp3.Interceptor
import okhttp3.Response

class JwtInterceptor(private val pref: MySharedPreference): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        pref.getToken().let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }
        return chain.proceed(requestBuilder.build())
    }
}