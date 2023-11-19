package com.sarang.profile.usecase

interface UnFollowUseCase {
    suspend fun invoke(id: Int): Boolean
}