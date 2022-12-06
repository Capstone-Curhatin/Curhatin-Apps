package com.capstone.core.di

import com.capstone.core.data.repository.*
import com.capstone.core.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [AppModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepoModule {

    @Binds
    abstract fun provideAuthentication(repo: AuthRepository): AuthRepositoryImpl

    @Binds
    abstract fun provideStoryRepository(repo: StoryRepository): StoryRepositoryImpl

    @Binds
    abstract fun provideUserRepository(repo: UserRepository): UserRepositoryImpl

    @Binds
    abstract fun provideWaitingRoomRepository(repo: WaitingRoomRepository): WaitingRoomRepositoryImpl

    @Binds
    abstract fun provideChatRepository(repo: ChatRepository): ChatRepositoryImpl

    @Binds
    abstract fun provideNotificationRepository(repo: NotificationRepository): NotificationRepositoryImpl

    @Binds
    abstract fun provideCommentRepository(repo: CommentRepository): CommentRepositoryImpl

    @Binds
    abstract fun provideDoctorRepository(repo: DoctorRepository): DoctorRepositoryImpl
}