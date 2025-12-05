package com.sarang.torang.usecase.profile

import com.sarang.torang.viewmodel.profile.FollowUiState

interface GetProfileUseCase {
    suspend fun invoke(userId : Int) : FollowUiState
}