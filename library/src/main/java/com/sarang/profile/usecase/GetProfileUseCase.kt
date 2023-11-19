package com.sarang.profile.usecase

import com.sarang.profile.viewmodel.FollowUiState

interface GetProfileUseCase {
    suspend fun invoke() : FollowUiState
}