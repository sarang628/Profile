package com.sarang.profile.usecase

interface DeleteUseCase {
    suspend fun invoke(id: Int): Boolean
}