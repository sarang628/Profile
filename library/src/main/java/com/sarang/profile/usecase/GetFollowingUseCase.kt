package com.sarang.profile.usecase

import com.sarang.profile.compose.follow.Follow

interface GetFollowingUseCase {
    suspend fun invoke(): List<Follow>
}