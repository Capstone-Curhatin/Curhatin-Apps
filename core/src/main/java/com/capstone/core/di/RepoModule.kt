package com.capstone.core.di

import com.capstone.core.data.repository.AuthRepository
import com.capstone.core.domain.reposoitory.AuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [AppModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepoModule {

    @Binds
    abstract fun provideAuthentication(repo: AuthRepository): AuthRepositoryImpl

}