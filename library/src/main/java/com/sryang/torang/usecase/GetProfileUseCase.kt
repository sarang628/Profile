package com.sryang.torang.usecase

import com.sryang.torang.viewmodel.FollowUiState

interface GetProfileUseCase {
    suspend fun invoke() : FollowUiState
}