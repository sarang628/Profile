package com.sarang.torang.usecase

import com.sarang.torang.Feed

interface GetMyFeedUseCase {
    suspend fun invoke(userId : Int): List<Feed>
}