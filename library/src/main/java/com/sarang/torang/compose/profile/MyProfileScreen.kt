package com.sarang.torang.compose.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sarang.torang.ProfileUiState
import com.sarang.torang.R
import com.sarang.torang.viewmodel.MyProfileViewModel
import com.sarang.torang.viewmodel.ProfileViewModel


/**
 * @param isMyProfile 내 프로필 여부
 * @param profileViewModel 프로필 뷰모델
 * @param onSetting 설정 클릭
 * @param favorite 즐겨찾기 컴포즈
 * @param wantToGo 가고싶다 컴포즈
 * @param onEditProfile 프로필 수정 클릭
 * @param onFollowing 팔로잉 클릭
 * @param onFollwer 팔로워 클릭
 * @param onWrite 게시글 클릭
 */
@Composable
fun MyProfileScreen(
    profileViewModel: MyProfileViewModel,
    onSetting: () -> Unit,
    favorite: @Composable () -> Unit,
    wantToGo: @Composable () -> Unit,
    onEditProfile: () -> Unit,
    onFollowing: () -> Unit,
    onFollwer: () -> Unit,
    onWrite: () -> Unit,
    onClose: () -> Unit,
    onEmailLogin: () -> Unit
) {
    val uiState by profileViewModel.uiState.collectAsState()
    val isLogin by profileViewModel.isLogin.collectAsState(initial = false)
    if (!isLogin) {
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
        return;
    }

    when (uiState) {
        is ProfileUiState.Success -> {
            MyProfileScreen(
                onSetting = onSetting,
                favorite = favorite,
                wantToGo = wantToGo,
                onEditProfile = onEditProfile,
                uiState = uiState as ProfileUiState.Success,
                onWrite = onWrite,
                onFollowing = onFollowing,
                onFollwer = onFollwer,
                onClearErrorMessage = { profileViewModel.onClearErrorMessage() }
            )
        }

        is ProfileUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        }

        is ProfileUiState.Error -> {}
    }
}

@Composable
fun MyProfileScreen(
    onSetting: () -> Unit,
    favorite: @Composable () -> Unit,
    wantToGo: @Composable () -> Unit,
    onEditProfile: () -> Unit,
    uiState: ProfileUiState.Success,
    onFollowing: () -> Unit,    // 팔로잉 클릭
    onFollwer: () -> Unit,      // 팔로워 클릭
    onWrite: () -> Unit,        // 게시글 클릭
    onClearErrorMessage: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
        )
        {
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