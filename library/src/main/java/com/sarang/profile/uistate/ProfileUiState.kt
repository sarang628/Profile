package com.sarang.profile.uistate

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class ProfileUiState(
    val profileUrl: String? = null,
    val feedCount: Int? = null,
    val follower: Int? = null,
    val following: Int? = null
)

fun testProfileUiState(): StateFlow<ProfileUiState> {
    val flow = MutableStateFlow(
        ProfileUiState(
            profileUrl = "http://sarang628.iptime.org:88/2.png",
            feedCount = 10,
            follower = 121,
            following = 32
        )
    )
    return flow
}