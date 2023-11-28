package com.sryang.torang.usecase.profile

interface DeleteUseCase {
    suspend fun invoke(id: Int): Boolean
}