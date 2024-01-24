package com.sarang.torang.profile

interface UnFollowUseCase {
    suspend fun invoke(id: Int): Boolean
}