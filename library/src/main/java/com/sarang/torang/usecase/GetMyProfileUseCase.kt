package com.sarang.torang.usecase

import com.sarang.torang.viewmodel.FollowUiState

interface GetMyProfileUseCase {
    suspend fun invoke() : FollowUiState
}