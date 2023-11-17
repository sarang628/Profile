package com.sarang.profile.usecase

interface IsLoginUseCase {
    val isLogin: kotlinx.coroutines.flow.Flow<Boolean>
}