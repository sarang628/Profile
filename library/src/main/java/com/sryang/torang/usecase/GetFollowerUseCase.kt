package com.sryang.torang.usecase

import com.sryang.torang.compose.follow.Follow

interface GetFollowerUseCase {
    suspend fun invoke(): List<Follow>
}