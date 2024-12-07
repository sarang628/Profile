package com.sarang.torang.usecase.profile

import com.sarang.torang.Feed

interface GetMyFeedUseCase {
    suspend fun invoke(userId : Int): List<Feed>
}