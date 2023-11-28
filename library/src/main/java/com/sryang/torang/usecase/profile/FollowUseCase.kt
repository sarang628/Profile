package com.sryang.torang.usecase.profile

interface FollowUseCase {
    suspend fun invoke(id: Int): Boolean
}