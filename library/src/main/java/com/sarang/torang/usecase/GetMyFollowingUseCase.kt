package com.sarang.torang.usecase

import com.sarang.torang.compose.follow.Follow

interface GetMyFollowingUseCase {
    suspend fun invoke(): List<Follow>
}