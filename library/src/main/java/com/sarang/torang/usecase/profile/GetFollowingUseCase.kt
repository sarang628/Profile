package com.sarang.torang.usecase.profile

import com.sarang.torang.compose.follow.Follow

interface GetFollowingUseCase {
    suspend fun invoke(userId: Int): List<Follow>
}