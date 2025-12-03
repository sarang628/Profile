package com.sarang.torang

sealed interface MyProfileUiState {

    object NeedLogin : MyProfileUiState
    object Loading : MyProfileUiState

    data class Success(val profileUrl  : String  = "",
                       val feedCount   : String  = "0",
                       val follower    : String  = "0",
                       val following   : String  = "0",
                       val name        : String  = "",
                       val isFollow    : Boolean = false) : MyProfileUiState

    data class Error(val message: String) : MyProfileUiState
}