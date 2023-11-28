package com.sryang.torang.usecase.profile

import com.sryang.torang.uistate.Feed
import com.sryang.torang.uistate.ProfileUiState


interface ProfileService {
    suspend fun loadProfile(i: Int): ProfileUiState
    suspend fun loadProfileByToken(): ProfileUiState
    suspend fun getFavorites(): kotlinx.coroutines.flow.Flow<List<Feed>>
    suspend fun updateProfile(uri: String)
}