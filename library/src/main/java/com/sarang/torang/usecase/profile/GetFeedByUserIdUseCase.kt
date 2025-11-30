package com.sarang.torang.usecase.profile

import com.sarang.torang.Feed

interface GetFeedByUserIdUseCase {
    suspend fun invoke(userId : Int): List<Feed>
}