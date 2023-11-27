package com.sryang.torang.usecase

interface FollowUseCase {
    suspend fun invoke(id: Int): Boolean
}