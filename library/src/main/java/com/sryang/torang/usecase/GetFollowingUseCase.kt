package com.sryang.torang.usecase

import com.sryang.torang.compose.follow.Follow

interface GetFollowingUseCase {
    suspend fun invoke(): List<Follow>
}