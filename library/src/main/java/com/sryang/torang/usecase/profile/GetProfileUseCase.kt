package com.sryang.torang.usecase.profile

import com.sryang.torang.viewmodel.FollowUiState

interface GetProfileUseCase {
    suspend fun invoke() : FollowUiState
}