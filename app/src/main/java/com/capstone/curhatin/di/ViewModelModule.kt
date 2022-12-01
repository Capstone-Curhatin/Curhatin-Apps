package com.capstone.curhatin.di

import com.capstone.core.domain.usecase.auth.AuthInteractor
import com.capstone.core.domain.usecase.auth.AuthUseCase
import com.capstone.core.domain.usecase.chat.ChatInteractor
import com.capstone.core.domain.usecase.chat.ChatUseCase
import com.capstone.core.domain.usecase.story.StoryInteractor
import com.capstone.core.domain.usecase.story.StoryUseCase
import com.capstone.core.domain.usecase.user.UserInteractor
import com.capstone.core.domain.usecase.user.UserUseCase
import com.capstone.core.domain.usecase.waiting.WaitingRoomInteractor
import com.capstone.core.domain.usecase.waiting.WaitingRoomUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewModelModule(){

    @Binds
    @ViewModelScoped
    abstract fun provideAuthUseCase(interactor: AuthInteractor): AuthUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideStoryUseCase(interactor: StoryInteractor): StoryUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideUserUseCase(interactor: UserInteractor): UserUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideWaitingRoomUseCase(interactor: WaitingRoomInteractor): WaitingRoomUseCase

    @Binds
    @ViewModelScoped
    abstract fun provideChatUseCase(interactor: ChatInteractor): ChatUseCase
}