package com.sarang.profile.compose.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sarang.profile.R
import com.sarang.profile.uistate.ProfileUiState
import com.sarang.profile.viewmodel.ProfileViewModel


@Composable
fun ProfileScreen(
    isMyProfile: Boolean,               // 내 프로필 여부
    profileImageUrl: String,            // 프로필 이미지 서버 url
    profileViewModel: ProfileViewModel, // 프로필 뷰모델
    onSetting: () -> Unit,              // 설정 클릭
    favorite: @Composable () -> Unit,   // 즐겨찾기 컴포즈
    wantToGo: @Composable () -> Unit,   // 가고싶다 컴포즈,
    onEditProfile: () -> Unit,          // 프로필 수정 클릭
    onFollowing: () -> Unit,            // 팔로잉 클릭
    onFollwer: () -> Unit,              // 팔로워 클릭
    onWrite: () -> Unit                 // 게시글 클릭
) {
    val uiState by profileViewModel.uiState.collectAsState()
    val isLogin by profileViewModel.isLogin.collectAsState(initial = false)
    if (isLogin) {
        _ProfileScreen(
            isMyProfile = isMyProfile,
            onSetting = onSetting,
            favorite = favorite,
            wantToGo = wantToGo,
            onEditProfile = onEditProfile,
            uiState = uiState,
            profileImageUrl = profileImageUrl,
            onWrite = onWrite,
            onFollowing = onFollowing,
            onFollwer = onFollwer,
            isFollow = uiState.isFollow,
            onFollow = { profileViewModel.follow() },
            onUnFollow = { profileViewModel.unFollow() },
            onClearErrorMessage = { profileViewModel.onClearErrorMessage() }
        )
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(text = "로그인을 해주세요.", modifier = Modifier.align(Alignment.Center), fontSize = 18.sp)
        }
    }
}

@Composable
fun _ProfileScreen(
    isMyProfile: Boolean,
    profileImageUrl: String,
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
    onClearErrorMessage: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
    )
    {
        if (isMyProfile) {
            Row(Modifier.padding(end = 8.dp, top = 8.dp)) {
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.ic_settings), contentDescription = "",
                    Modifier.clickable {
                        onSetting.invoke()
                    }
                )
            }
        }

        Column(modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 20.dp)) {
            ProfileSummary(
                profileUrl = profileImageUrl + uiState.profileUrl,
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


@Preview
@Composable
fun PreviewProfileScreen() {
    _ProfileScreen(
        isMyProfile = false,
        onSetting = { /*TODO*/ },
        favorite = { /*TODO*/ },
        wantToGo = { /*TODO*/ },
        onEditProfile = { /*TODO*/ },
        uiState = ProfileUiState(
            profileUrl = "",
            feedCount = 0,
            follower = 0,
            following = 0,
            name = "",
            isLogin = false,
            favoriteList = ArrayList(),
            id = 0
        ),
        profileImageUrl = "",
        onFollwer = {},
        onFollowing = {},
        onWrite = {},
        isFollow = false,
        onUnFollow = {},
        onFollow = {},
        onClearErrorMessage = {}
    )
}