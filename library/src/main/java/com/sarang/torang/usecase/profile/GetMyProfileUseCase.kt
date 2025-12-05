package com.sarang.torang.usecase.profile

import com.sarang.torang.compose.profile.MyProfileUiState
import kotlinx.coroutines.flow.Flow

interface GetMyProfileUseCase {
    fun invoke() : Flow<MyProfileUiState>
}