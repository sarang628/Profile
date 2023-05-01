package com.sarang.profile.uistate

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class ProfileUiState(
    val profileUrl: String
)

fun testProfileUiState(): StateFlow<ProfileUiState> {
    val flow = MutableStateFlow(ProfileUiState(profileUrl = ""))
    return flow
}