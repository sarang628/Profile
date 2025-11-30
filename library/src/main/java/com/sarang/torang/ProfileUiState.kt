package com.sarang.torang

sealed interface ProfileUiState {

    object Login : ProfileUiState
    object Loading : ProfileUiState

    data class Success(val id          : Int     = 0,
                       val profileUrl  : String  = "",
                       val feedCount   : String  = "0",
                       val follower    : String  = "0",
                       val following   : String  = "0",
                       val name        : String  = "",
                       val isFollow    : Boolean = false) : ProfileUiState

    data class Error(val message: String) : ProfileUiState
}