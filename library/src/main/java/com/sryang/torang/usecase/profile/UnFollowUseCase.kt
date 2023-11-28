package com.sryang.torang.usecase.profile

interface UnFollowUseCase {
    suspend fun invoke(id: Int): Boolean
}