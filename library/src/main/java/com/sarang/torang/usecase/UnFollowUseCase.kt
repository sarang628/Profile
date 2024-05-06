package com.sarang.torang.usecase

interface UnFollowUseCase {
    suspend fun invoke(id: Int): Boolean
}