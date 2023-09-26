package com.sarang.profile.viewmodel

import com.sarang.profile.uistate.ProfileUiState


interface ProfileService {
    suspend fun loadProfile(i: Int) : ProfileUiState
}