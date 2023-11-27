package com.sryang.torang.usecase

interface DeleteUseCase {
    suspend fun invoke(id: Int): Boolean
}