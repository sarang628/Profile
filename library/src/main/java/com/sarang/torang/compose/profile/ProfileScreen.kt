package com.sarang.torang.compose.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
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
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.torang.ProfileUiState
import com.sarang.torang.compose.myfeed.FeedListScreen
import com.sarang.torang.compose.profile.components.FavoriteAndWantToGo
import com.sarang.torang.compose.profile.components.ProfileSummary
import com.sarang.torang.viewmodel.ProfileViewModel
import kotlinx.coroutines.launch


/**
 * @param profileViewModel 프로필 뷰모델
 * @param onSetting 설정 클릭
 * @param favorite 즐겨찾기 컴포즈
 * @param wantToGo 가고싶다 컴포즈
 * @param onEditProfile 프로필 수정 클릭
 * @param onFollowing 팔로잉 클릭
 * @param onFollwer 팔로워 클릭
 * @param onWrite 게시글 클릭
 * @param onMessage 메시지 클릭 (채팅방 id를 찾아 파라미터로 전달)
 */
@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = hiltViewModel(),
    onFollowing: () -> Unit,
    onFollwer: () -> Unit,
    onWrite: () -> Unit,
    onClose: () -> Unit,
    onEmailLogin: () -> Unit,
    onMessage: (Int) -> Unit,
    id: Int? = null,
    onReview: ((Int) -> Unit)? = null,
) {
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
            val feedListScreen = @Composable {
                FeedListScreen(userId = (uiState as ProfileUiState.Success).id,
                               onReview = onReview)
            }
            Profile(
                uiState = uiState as ProfileUiState.Success,
                onWrite = onWrite,
                onFollowing = onFollowing,
                onFollwer = onFollwer,
                isFollow = (uiState as ProfileUiState.Success).isFollow,
                onFollow = { profileViewModel.follow() },
                onUnFollow = { profileViewModel.unFollow() },
                onClearErrorMessage = { profileViewModel.onClearErrorMessage() },
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
    }
}

@Preview(showBackground = true)
@Composable
fun Profile(
    uiState: ProfileUiState.Success = ProfileUiState.Success(),
    onFollowing: () -> Unit = {},
    onFollwer: () -> Unit = {},
    onWrite: () -> Unit = {},
    isFollow: Boolean = false,
    onFollow: () -> Unit = {},
    onUnFollow: () -> Unit = {},
    onMessage: () -> Unit = {},
    onClearErrorMessage: () -> Unit = {},
    onClose: (() -> Unit)? = null,
    isLogin: Boolean = false,
    feedListScreen: @Composable () -> Unit = {},
) {
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        ProfileTopAppBar(name = uiState.name, onBack = {
            onClose?.invoke()
        })
    }) { padding ->
        Box(
            modifier = Modifier.padding(padding)
        ) {
            Column(modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 10.dp)) {
                ProfileSummary(
                    profileUrl = uiState.profileUrl,
                    feedCount = uiState.feedCount,
                    follower = uiState.follower,
                    following = uiState.following,
                    name = uiState.name,
                    onFollwer = onFollwer,
                    onFollowing = onFollowing,
                    onWrite = onWrite,
                )
                    if (isLogin) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(modifier = Modifier.height(35.dp)) {
                            Button( shape = RoundedCornerShape(8.dp),
                                    onClick = { if (isFollow) onUnFollow.invoke()
                                                else onFollow.invoke() },
                                    modifier = Modifier.weight(1f),) {
                            Text( text = if (!isFollow) "Follow" else "UnFollow",
                                  color = Color.White)
                        }
                        Spacer(modifier = Modifier.width(3.dp))
                        Button( shape = RoundedCornerShape(8.dp), 
                                onClick = onMessage,
                                modifier = Modifier.weight(1f)) {
                            Text(text = "Message")
                        }
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
                FavoriteAndWantToGo(wantToGo = {
                    feedListScreen.invoke()
                }, favorite = {
                    feedListScreen.invoke()
                })
            }
            uiState.errorMessage?.let {
                AlertDialog(onDismissRequest = { /*TODO*/ }, confirmButton = {
                    Button(onClick = { onClearErrorMessage.invoke() }) {
                        Text(text = "확인")
                    }
                }, text = { Text(text = it, fontSize = 14.sp) })
            }
        }
    }
}

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTopAppBar(name: String = "",
                     onBack: () -> Unit = {}) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    TopAppBar(
        modifier = Modifier.height(50.dp),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
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