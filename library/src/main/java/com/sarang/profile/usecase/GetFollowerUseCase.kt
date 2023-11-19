package com.sarang.profile.usecase

import com.sarang.profile.compose.follow.Follow

interface GetFollowerUseCase {
    suspend fun invoke(): List<Follow>
}