package com.sarang.torang.usecase

interface IsLoginUseCase {
    val isLogin: kotlinx.coroutines.flow.Flow<Boolean>
}