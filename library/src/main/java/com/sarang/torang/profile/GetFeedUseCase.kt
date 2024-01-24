package com.sarang.torang.profile

import com.sarang.torang.Feed

interface GetFeedUseCase {
    suspend fun invoke(userId : Int): List<Feed>
}