package com.sarang.profile.usecase

interface DeleteUseCase {
    fun invoke(id: Int): Boolean
}