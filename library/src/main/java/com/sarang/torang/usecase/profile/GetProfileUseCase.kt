package com.sarang.torang.usecase.profile

import com.sarang.torang.viewmodel.FollowUiState

interface GetProfileUseCase {
    suspend fun invoke(userId : Int) : FollowUiState
}