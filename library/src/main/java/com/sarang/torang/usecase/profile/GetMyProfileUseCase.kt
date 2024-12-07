package com.sarang.torang.usecase.profile

import com.sarang.torang.viewmodel.FollowUiState

interface GetMyProfileUseCase {
    suspend fun invoke() : FollowUiState
}