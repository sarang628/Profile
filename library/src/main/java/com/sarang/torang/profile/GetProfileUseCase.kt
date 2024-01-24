package com.sarang.torang.profile

import com.sarang.torang.viewmodel.FollowUiState

interface GetProfileUseCase {
    suspend fun invoke() : FollowUiState
}