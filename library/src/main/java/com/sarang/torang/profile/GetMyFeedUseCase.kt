package com.sarang.torang.profile

import com.sarang.torang.Feed

interface GetMyFeedUseCase {
    suspend fun invoke(userId : Int): List<Feed>
}