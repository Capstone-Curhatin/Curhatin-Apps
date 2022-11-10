package com.capstone.core.di

import android.content.Context
import com.capstone.core.BuildConfig
import com.capstone.core.BuildConfig.BASE_URL
import com.capstone.core.data.common.MyDispatchers
import com.capstone.core.data.common.SafeCall
import com.capstone.core.data.network.AuthService
import com.capstone.core.data.network.connection.JwtInterceptor
import com.capstone.core.utils.MySharedPreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSafeCall() = SafeCall()

    @Provides
    @Singleton
    fun provideMyDispatchers() = MyDispatchers()

    @Provides
    @Singleton
    fun provideMySharedPreference(@ApplicationContext context: Context) = MySharedPreference(context)

    @Provides
    @Singleton
    fun provideOkHttpInterceptor() = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        interceptor: HttpLoggingInterceptor,
        prefs: MySharedPreference
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(JwtInterceptor(prefs))
        .addInterceptor(interceptor)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)
}