package com.sarang.torang.usecase.profile

interface FindOrCreateChatRoomByUserIdUseCase {
    suspend fun invoke(userId: Int): Int
}