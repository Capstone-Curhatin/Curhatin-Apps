package com.capstone.core.di

import android.content.Context
import com.capstone.core.BuildConfig
import com.capstone.core.BuildConfig.BASE_URL
import com.capstone.core.data.common.ErrorParser
import com.capstone.core.data.common.MyDispatchers
import com.capstone.core.data.common.SafeCall
import com.capstone.core.data.network.AuthService
import com.capstone.core.data.network.DoctorService
import com.capstone.core.data.network.StoryService
import com.capstone.core.data.network.UserService
import com.capstone.core.data.network.connection.JwtInterceptor
import com.capstone.core.data.source.*
import com.capstone.core.data.source.firebase.ChatStorage
import com.capstone.core.data.source.firebase.CommentStorage
import com.capstone.core.data.source.firebase.NotificationStorage
import com.capstone.core.data.source.firebase.WaitingRoomStorage
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

    @Provides
    @Singleton
    fun provideStoryService(retrofit: Retrofit): StoryService =
        retrofit.create(StoryService::class.java)

    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)

    @Provides
    @Singleton
    fun provideDoctorService(retrofit: Retrofit): DoctorService =
        retrofit.create(DoctorService::class.java)

    @Provides
    @Singleton
    fun provideAuthDataSource(
        safeCall: SafeCall,
        errorParser: ErrorParser,
        service: AuthService,
        dispatcher: MyDispatchers
    ) = AuthDataSource(safeCall, errorParser, service, dispatcher)

    @Provides
    @Singleton
    fun provideStoryDataSource(
        safeCall: SafeCall,
        dispatcher: MyDispatchers,
        errorParser: ErrorParser,
        service: StoryService
    ) = StoryDataSource(safeCall, dispatcher, errorParser, service)

    @Provides
    @Singleton
    fun provideUserDataSource(
        safeCall: SafeCall,
        dispatcher: MyDispatchers,
        errorParser: ErrorParser,
        service: UserService,
        prefs: MySharedPreference
    ) = UserDataSource(safeCall, dispatcher, errorParser, service, prefs)

    @Provides
    @Singleton
    fun provideDoctorDataSource(
        safeCall: SafeCall,
        dispatcher: MyDispatchers,
        errorParser: ErrorParser,
        service: DoctorService
    ) = DoctorDataSource(safeCall, dispatcher, errorParser, service)

    @Provides
    @Singleton
    fun provideWaitingRoomDataSource(): WaitingRoomStorage = WaitingRoomDataSource()

    @Provides
    @Singleton
    fun provideChatDataSource(): ChatStorage = ChatDataSource()

    @Provides
    @Singleton
    fun provideNotificationDataSource(): NotificationStorage = NotificationDataSource()

    @Provides
    @Singleton
    fun provideCommentDataSource(): CommentStorage = CommentDataSource()
}