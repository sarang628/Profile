package com.sarang.torang.usecase.profile

import com.sarang.torang.viewmodel.profile.MyLikeListViewUiState
import kotlinx.coroutines.flow.Flow

interface FineMyLikeFlowUseCase {
    fun invoke(): Flow<List<MyLikeListViewUiState>>
}