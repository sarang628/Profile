package com.sarang.profile.compose.follow

data class Follow(
    val url: String,
    val id: Int,
    val name: String,
    val nickname: String,
    val isFollow: Boolean
)
