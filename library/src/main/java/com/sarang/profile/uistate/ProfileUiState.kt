package com.sarang.profile.uistate

data class ProfileUiState(
    val id: Int,
    val profileUrl: String,
    val feedCount: Int,
    val follower: Int,
    val following: Int,
    val name: String,
    val isLogin: Boolean = false,
    val favoriteList: List<Feed>? = null,
    val isFollow: Boolean = false,
    val errorMessage: String? = null
)