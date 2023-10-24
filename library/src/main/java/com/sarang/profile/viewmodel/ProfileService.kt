package com.sarang.profile.viewmodel

import com.sarang.profile.uistate.Feed
import com.sarang.profile.uistate.ProfileUiState


interface ProfileService {
    suspend fun loadProfile(i: Int): ProfileUiState
    suspend fun loadProfileByToken(): ProfileUiState
    suspend fun getFavorites(): kotlinx.coroutines.flow.Flow<List<Feed>>
    suspend fun updateProfile(uri: String)
}