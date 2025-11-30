package com.sarang.torang.usecase.profile

import com.sarang.torang.MyProfileUiState
import kotlinx.coroutines.flow.Flow

interface GetMyProfileUseCase {
    fun invoke() : Flow<MyProfileUiState.Success>
}