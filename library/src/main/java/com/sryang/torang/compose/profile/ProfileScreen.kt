package com.sryang.torang.compose.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sryang.torang.R
import com.sryang.torang.uistate.ProfileUiState
import com.sryang.torang.viewmodel.ProfileViewModel


@Composable
fun ProfileScreen(
    isMyProfile: Boolean,               // 내 프로필 여부
    profileViewModel: ProfileViewModel, // 프로필 뷰모델
    onSetting: () -> Unit,              // 설정 클릭
    favorite: @Composable () -> Unit,   // 즐겨찾기 컴포즈
    wantToGo: @Composable () -> Unit,   // 가고싶다 컴포즈,
    onEditProfile: () -> Unit,          // 프로필 수정 클릭
    onFollowing: () -> Unit,            // 팔로잉 클릭
    onFollwer: () -> Unit,              // 팔로워 클릭
    onWrite: () -> Unit,                // 게시글 클릭
    onClose: () -> Unit,
    onEmailLogin: () -> Unit
) {
    val uiState by profileViewModel.uiState.collectAsState()
    val isLogin by profileViewModel.isLogin.collectAsState(initial = false)
    if (isLogin) {
        ProfileScreen(
            isMyProfile = isMyProfile,
            onSetting = onSetting,
            favorite = favorite,
            wantToGo = wantToGo,
            onEditProfile = onEditProfile,
            uiState = uiState,
            onWrite = onWrite,
            onFollowing = onFollowing,
            onFollwer = onFollwer,
            isFollow = uiState.isFollow,
            onFollow = { profileViewModel.follow() },
            onUnFollow = { profileViewModel.unFollow() },
            onClearErrorMessage = { profileViewModel.onClearErrorMessage() },
            onClose = {
                onClose.invoke()
            }
        )
    } else {
        Box(Modifier.fillMaxSize()) {
            Column(
                Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "로그인을 해주세요.")
                Button(onClick = { onEmailLogin.invoke() }) {
                    Text(text = "LOG IN WITH EMAIL")
                }
            }
        }
    }
}

@Composable
fun ProfileScreen(
    isMyProfile: Boolean,
    onSetting: () -> Unit,
    favorite: @Composable () -> Unit,
    wantToGo: @Composable () -> Unit,
    onEditProfile: () -> Unit,
    uiState: ProfileUiState,
    onFollowing: () -> Unit,    // 팔로잉 클릭
    onFollwer: () -> Unit,      // 팔로워 클릭
    onWrite: () -> Unit,        // 게시글 클릭
    isFollow: Boolean,          // 팔로우 여부
    onFollow: () -> Unit,
    onUnFollow: () -> Unit,
    onClearErrorMessage: () -> Unit,
    onClose: (() -> Unit)? = null
) {
    val InteractionSource = remember { MutableInteractionSource() }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (!isMyProfile) {
                ProfileTopAppBar(name = uiState.name, onBack = {
                    onClose?.invoke()
                })
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
        )
        {
            if (isMyProfile) {
                Row(Modifier.padding(end = 8.dp, top = 8.dp)) {
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(id = R.drawable.ic_settings),
                        contentDescription = "",
                        Modifier.clickable {
                            onSetting.invoke()
                        }
                    )
                }
            }

            Column(modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 20.dp)) {
                ProfileSummary(
                    profileUrl = uiState.profileUrl,
                    feedCount = uiState.feedCount,
                    follower = uiState.follower,
                    following = uiState.following,
                    name = uiState.name,
                    onFollwer = onFollwer,
                    onFollowing = onFollowing,
                    onWrite = onWrite
                )
                Spacer(modifier = Modifier.height(30.dp))
                if (isMyProfile) {
                    Row {
                        Button(
                            onClick = {
                                onEditProfile.invoke()
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(40.dp),
                        ) {
                            Text(
                                text = "프로필 편집",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp
                            )
                        }
                    }
                } else {
                    Row {
                        Button(
                            onClick = {
                                if (isFollow)
                                    onUnFollow.invoke()
                                else
                                    onFollow.invoke()
                            },
                            modifier = Modifier
                                .weight(1f)
                                .height(40.dp),
                        ) {
                            Text(
                                text = if (!isFollow) "Follow" else "UnFollow",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
                FavoriteAndWantToGo(
                    wantToGo = { wantToGo.invoke() },
                    favorite = { favorite.invoke() }
                )
            }
            uiState.errorMessage?.let {
                AlertDialog(onDismissRequest = { /*TODO*/ },
                    confirmButton = {
                        Button(onClick = { onClearErrorMessage.invoke() }) {
                            Text(text = "확인")
                        }
                    },
                    text =
                    { Text(text = it, fontSize = 14.sp) })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTopAppBar(name: String, onBack: () -> Unit) {
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


@Preview
@Composable
fun PreviewProfileScreen() {
    ProfileScreen(
        isMyProfile = true,
        onSetting = { /*TODO*/ },
        favorite = { /*TODO*/ },
        wantToGo = { /*TODO*/ },
        onEditProfile = { /*TODO*/ },
        uiState = ProfileUiState(
            profileUrl = "",
            feedCount = 0,
            follower = 0,
            following = 0,
            name = "Amanda",
            isLogin = false,
            favoriteList = ArrayList(),
            id = 0
        ),
        onFollwer = {},
        onFollowing = {},
        onWrite = {},
        isFollow = false,
        onUnFollow = {},
        onFollow = {},
        onClearErrorMessage = {}
    )
}