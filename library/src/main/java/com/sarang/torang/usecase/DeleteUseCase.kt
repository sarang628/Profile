package com.sarang.torang.usecase

interface DeleteUseCase {
    suspend fun invoke(id: Int): Boolean
}