package com.sarang.torang.usecase.profile

import com.sarang.torang.viewmodel.profile.MyFavoriteItemUiState
import kotlinx.coroutines.flow.Flow

interface FineMyFavoriteFlowUseCase {
    fun invoke(): Flow<List<MyFavoriteItemUiState>>
}