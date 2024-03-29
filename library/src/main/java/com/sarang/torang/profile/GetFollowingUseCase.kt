package com.sarang.torang.profile

import com.sarang.torang.compose.follow.Follow

interface GetFollowingUseCase {
    suspend fun invoke(): List<Follow>
}