package com.sryang.torang.usecase

interface UnFollowUseCase {
    suspend fun invoke(id: Int): Boolean
}