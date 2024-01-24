package com.sarang.torang.profile

interface DeleteUseCase {
    suspend fun invoke(id: Int): Boolean
}