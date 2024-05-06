package com.sarang.torang.usecase

import com.sarang.torang.compose.follow.Follow

interface GetFollowerUseCase {
    suspend fun invoke(userId : Int): List<Follow>
}