package com.sarang.torang.usecase.profile

interface DeleteUseCase {
    suspend fun invoke(id: Int): Boolean
}