package com.sarang.torang

sealed interface ProfileUiState {

    object Loading : ProfileUiState

    data class Success(
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
    ) : ProfileUiState

    data class Error(val message: String) : ProfileUiState
}