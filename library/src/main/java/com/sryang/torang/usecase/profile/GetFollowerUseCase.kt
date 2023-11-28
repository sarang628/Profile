package com.sryang.torang.usecase.profile

import com.sryang.torang.compose.follow.Follow

interface GetFollowerUseCase {
    suspend fun invoke(): List<Follow>
}