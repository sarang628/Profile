package com.sarang.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sarang.profile.viewmodel.ProfileViewModel


@Composable
fun _ProfileScreen(
    isMyProfile: Boolean,
    profileImageUrl: String = "",
    profileViewModel: ProfileViewModel,
    onSetting: () -> Unit,
    favorite: @Composable () -> Unit,
    wantToGo: @Composable () -> Unit,
    onEditProfile: () -> Unit
) {
    val uiState by profileViewModel.uiState.collectAsState()

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
                profileBaseUrl = profileImageUrl,
                profileUrl = uiState.profileUrl,
                feedCount = uiState.feedCount,
                follower = uiState.follower,
                following = uiState.following,
                name = uiState.name
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
            }
            Spacer(modifier = Modifier.height(5.dp))
            FavoriteAndWantToGo(
                wantToGo = { wantToGo.invoke() },
                favorite = { favorite.invoke() }
            )
        }
    }
}