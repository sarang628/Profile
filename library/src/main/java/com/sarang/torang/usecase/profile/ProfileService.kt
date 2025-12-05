package com.sarang.torang.usecase.profile

import com.sarang.torang.Feed
import com.sarang.torang.ProfileUiState
import kotlinx.coroutines.flow.Flow


interface ProfileService {
    suspend fun loadProfile(i: Int): ProfileUiState
    suspend fun loadProfileByToken(): ProfileUiState
    suspend fun getFavorites(): Flow<List<Feed>>
    suspend fun updateProfile(uri: String)
}