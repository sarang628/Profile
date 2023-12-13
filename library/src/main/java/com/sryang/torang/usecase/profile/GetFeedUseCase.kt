package com.sryang.torang.usecase.profile

import com.sryang.torang.uistate.Feed

interface GetFeedUseCase {
    suspend fun invoke(userId : Int): List<Feed>
}