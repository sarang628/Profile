package com.sarang.profile.uistate

data class ProfileUiState(
    val profileUrl: String,
    val feedCount: Int,
    val follower: Int,
    val following: Int,
    val name: String,
    val isLogin: Boolean = false
)