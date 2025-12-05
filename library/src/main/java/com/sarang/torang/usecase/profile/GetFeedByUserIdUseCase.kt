package com.sarang.torang.usecase.profile

import com.sarang.torang.data.profile.FeedListItemUIState

interface GetFeedByUserIdUseCase {
    suspend fun invoke(userId : Int) : List<FeedListItemUIState>
}