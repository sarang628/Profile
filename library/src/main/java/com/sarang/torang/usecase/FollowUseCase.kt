package com.sarang.torang.usecase

interface FollowUseCase {
    suspend fun invoke(id: Int): Boolean
}