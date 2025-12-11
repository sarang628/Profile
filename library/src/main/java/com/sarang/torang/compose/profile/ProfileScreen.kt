package com.sarang.torang.compose.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.torang.compose.myfeed.FeedListScreen
import com.sarang.torang.compose.profile.components.MyFeedsLikesFavoritiesList
import com.sarang.torang.compose.profile.components.ProfileSummary
import com.sarang.torang.viewmodel.profile.ProfileViewModel
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(profileViewModel    : ProfileViewModel  = hiltViewModel(),
                  id                  : Int?              = null,
                  onFollowing         : () -> Unit        = { },
                  onFollower          : () -> Unit        = { },
                  onWrite             : () -> Unit        = { },
                  onClose             : () -> Unit        = { },
                  onEmailLogin        : () -> Unit        = { },
                  onMessage           : (Int) -> Unit     = { },
                  onReview            : (Int) -> Unit     = { }) {
    val uiState by profileViewModel.uiState.collectAsState()
    val isLogin by profileViewModel.isLogin.collectAsState(initial = false)
    val coroutine = rememberCoroutineScope()

    LaunchedEffect(key1 = id, block = {
        id?.let {
            profileViewModel.loadProfile(it)
        }
    })

    if (id == null) {
        Text(text = "사용자 정보가 없습니다.")
        return
    }

    when (uiState) {
        is ProfileUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        }

        is ProfileUiState.Success -> {
            val feedListScreen : @Composable (Modifier) -> Unit = @Composable {
                FeedListScreen(modifier = it,
                               userId = (uiState as ProfileUiState.Success).id,
                               onReview = onReview)
            }
            Profile(
                uiState = uiState as ProfileUiState.Success,
                onWrite = onWrite,
                onFollowing = onFollowing,
                onFollower = onFollower,
                isFollow = (uiState as ProfileUiState.Success).isFollow,
                onFollow = { profileViewModel.follow() },
                onUnFollow = { profileViewModel.unFollow() },
                onClose = onClose,
                isLogin = isLogin,
                onMessage = {
                    coroutine.launch {
                        onMessage.invoke(
                            profileViewModel.findOrCreateChatRoomByUserId(id)
                        )
                    }
                },
                feedListScreen = feedListScreen
            )
        }

        is ProfileUiState.Error -> {}
        is ProfileUiState.Login -> {}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun Profile(uiState         : ProfileUiState.Success    = ProfileUiState.Success(),
            onFollowing     : () -> Unit                = {},
            onFollower      : () -> Unit                = {},
            onWrite         : () -> Unit                = {},
            isFollow        : Boolean                   = false,
            onFollow        : () -> Unit                = {},
            onUnFollow      : () -> Unit                = {},
            onMessage       : () -> Unit                = {},
            onClose         : () -> Unit                = {},
            isLogin         : Boolean                   = false,
            feedListScreen  : @Composable (Modifier) -> Unit    = {}) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
            contentWindowInsets = WindowInsets(left = 8.dp, right = 8.dp, top = 10.dp),
             topBar = { ProfileTopAppBar(name = uiState.name,
                                         onBack = onClose,
                                         scrollBehavior = scrollBehavior) }) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            item {
                ProfileSummary(profileUrl   = uiState.profileUrl,
                    feedCount    = uiState.feedCount,
                    follower     = uiState.follower,
                    following    = uiState.following,
                    name         = uiState.name,
                    onFollower   = onFollower,
                    onFollowing  = onFollowing,
                    onWrite      = onWrite)
                if (isLogin) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(modifier = Modifier.height(35.dp)) {
                        Button(shape = RoundedCornerShape(8.dp),
                            onClick = { if (isFollow) onUnFollow.invoke()
                            else onFollow.invoke() },
                            modifier = Modifier.weight(1f),) {
                            Text(text = if (!isFollow) "Follow" else "UnFollow",
                                color = Color.White)
                        }
                        Spacer(modifier = Modifier.width(3.dp))
                        Button(shape = RoundedCornerShape(8.dp),
                            onClick = onMessage,
                            modifier = Modifier.weight(1f)) {
                            Text(text = "Message")
                        }
                    }
                }
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
            item {
                feedListScreen.invoke(Modifier.fillParentMaxHeight())
            }
        }
    }
}

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTopAppBar(name: String = "",
                     onBack: () -> Unit = {},
                     scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
) {
    TopAppBar(colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background,
                                                         titleContentColor = MaterialTheme.colorScheme.primary),
        title = {
            Box(modifier = Modifier.fillMaxHeight()) {
                Text(
                    name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.align(Alignment.CenterStart),
                    fontSize = 20.sp
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = { onBack.invoke() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "Localized description"
                )
            }
        },
        scrollBehavior = scrollBehavior,
    )
}