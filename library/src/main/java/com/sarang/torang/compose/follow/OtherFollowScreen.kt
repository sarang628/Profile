package com.sarang.torang.compose.follow

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.torang.viewmodel.OtherFollowViewModel

@Composable
fun OtherFollowScreen(
    followViewModel: OtherFollowViewModel = hiltViewModel(),
    onBack: () -> Unit,
    userId : Int,
    onProfile: ((Int) -> Unit)? = null,
    page: Int? = null,
    image: @Composable (Modifier, String, Dp?, Dp?, ContentScale?) -> Unit
) {
    val follower by followViewModel.follower.collectAsState()
    val following by followViewModel.following.collectAsState()
    val subscription by followViewModel.subscription.collectAsState()
    val uiState by followViewModel.uiState.collectAsState()
    val errorMessage by followViewModel.errorMessage.collectAsState()

    LaunchedEffect(key1 = userId) {
        followViewModel.init(userId)
    }

    _FollowScreen(
        name = uiState.name,
        following = uiState.following,
        follower = uiState.follower,
        subscription = uiState.subscription,
        followerList = follower,
        followingList = following,
        subscriptionList = subscription,
        errorMessage = errorMessage,
        onClearErrorMessage = { followViewModel.clearErrorMessage() },
        onBack = onBack,
        onUnFollow = { followViewModel.unFollow(it) },
        onDelete = { followViewModel.delete(it) },
        onProfile = onProfile,
        page = page,
        image = image
    )
}