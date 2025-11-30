package com.sarang.torang.usecase.profile

import com.sarang.torang.viewmodel.FollowUiState
import kotlinx.coroutines.flow.Flow

interface GetMyProfileUseCase {
    fun invoke() : Flow<FollowUiState>
}