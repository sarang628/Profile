package com.sarang.torang.usecase

import com.sarang.torang.Feed
import com.sarang.torang.ProfileUiState


interface ProfileService {
    suspend fun loadProfile(i: Int): ProfileUiState
    suspend fun loadProfileByToken(): ProfileUiState
    suspend fun getFavorites(): kotlinx.coroutines.flow.Flow<List<Feed>>
    suspend fun updateProfile(uri: String)
}