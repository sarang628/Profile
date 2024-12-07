package com.sarang.torang.usecase.profile

interface FollowUseCase {
    suspend fun invoke(id: Int): Boolean
}