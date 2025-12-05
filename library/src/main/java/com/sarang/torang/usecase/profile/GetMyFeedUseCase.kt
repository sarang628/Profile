package com.sarang.torang.usecase.profile

import com.sarang.torang.data.profile.Feed
import kotlinx.coroutines.flow.Flow

interface GetMyFeedUseCase {
    fun invoke(): Flow<List<Feed>>
}