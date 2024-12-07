package com.sarang.torang.usecase.profile

import com.sarang.torang.compose.follow.Follow

interface GetMyFollowerUseCase {
    suspend fun invoke(): List<Follow>
}