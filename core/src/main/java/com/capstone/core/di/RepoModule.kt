package com.capstone.core.di

import com.capstone.core.data.repository.AuthRepository
import com.capstone.core.data.repository.StoryRepository
import com.capstone.core.data.repository.UserRepository
import com.capstone.core.data.repository.WaitingRoomRepository
import com.capstone.core.domain.repository.AuthRepositoryImpl
import com.capstone.core.domain.repository.StoryRepositoryImpl
import com.capstone.core.domain.repository.UserRepositoryImpl
import com.capstone.core.domain.repository.WaitingRoomRepositoryImpl
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
}