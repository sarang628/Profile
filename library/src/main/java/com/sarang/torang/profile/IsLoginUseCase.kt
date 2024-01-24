package com.sarang.torang.profile

interface IsLoginUseCase {
    val isLogin: kotlinx.coroutines.flow.Flow<Boolean>
}