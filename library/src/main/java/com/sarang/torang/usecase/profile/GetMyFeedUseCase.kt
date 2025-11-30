package com.sarang.torang.usecase.profile

import com.sarang.torang.Feed
import kotlinx.coroutines.flow.Flow

interface GetMyFeedUseCase {
    fun invoke(): Flow<List<Feed>>
}