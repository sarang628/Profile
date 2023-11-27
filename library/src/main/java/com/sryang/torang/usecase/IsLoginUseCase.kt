package com.sryang.torang.usecase

interface IsLoginUseCase {
    val isLogin: kotlinx.coroutines.flow.Flow<Boolean>
}