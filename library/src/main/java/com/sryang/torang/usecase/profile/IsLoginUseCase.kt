package com.sryang.torang.usecase.profile

interface IsLoginUseCase {
    val isLogin: kotlinx.coroutines.flow.Flow<Boolean>
}