package com.sryang.torang.compose.follow

data class Follow(
    val url: String,
    val id: Int,
    val name: String,
    val nickname: String,
    val isFollow: Boolean
)
