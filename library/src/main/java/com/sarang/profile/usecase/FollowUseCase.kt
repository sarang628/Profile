package com.sarang.profile.usecase

interface FollowUseCase {
    suspend fun invoke(id: Int): Boolean
}