package com.sarang.torang.usecase

import com.sarang.torang.compose.follow.Follow

interface GetMyFollowerUseCase {
    suspend fun invoke(): List<Follow>
}