package com.sarang.torang.profile

import com.sarang.torang.viewmodel.FollowUiState

interface GetMyProfileUseCase {
    suspend fun invoke() : FollowUiState
}