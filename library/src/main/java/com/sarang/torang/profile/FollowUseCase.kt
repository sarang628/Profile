package com.sarang.torang.profile

interface FollowUseCase {
    suspend fun invoke(id: Int): Boolean
}