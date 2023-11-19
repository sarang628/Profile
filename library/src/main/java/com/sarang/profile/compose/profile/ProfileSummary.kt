package com.sarang.profile.compose.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun ProfileSummary(
    profileUrl: String,         // 프로필 이미지 url
    name: String,               // 이름
    feedCount: Int,             // 피드 수
    follower: Int,              // 팔로워 수
    following: Int,             // 팔로잉 수
    onFollowing: () -> Unit,    // 팔로잉 클릭
    onFollwer: () -> Unit,      // 팔로워 클릭
    onWrite: () -> Unit         // 게시글 클릭
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column {
            AsyncImage(
                model = profileUrl,
                contentDescription = "",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color(0x11000000)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = name,
                Modifier.padding(start = 8.dp),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Row(
            Modifier
                .weight(1f)
                .padding(start = 16.dp)
        ) {
            Column(
                Modifier
                    .weight(1f)
                    .clickable { onWrite.invoke() },
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Text(
                    text = feedCount.toString(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = "게시물", fontSize = 18.sp)
            }

            Column(
                Modifier
                    .weight(1f)
                    .clickable {
                        onFollowing.invoke()
                    }, horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Text(
                    text = follower.toString(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = "팔로워", fontSize = 18.sp)
            }

            Column(
                Modifier
                    .weight(1f)
                    .clickable {
                        onFollowing.invoke()
                    }, horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Text(
                    text = following.toString(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = "팔로잉", fontSize = 18.sp)
            }
        }

    }
}

@Preview
@Composable
fun PreviewProfileSummary() {
    ProfileSummary(
        profileUrl = "",
        name = "name",
        feedCount = 11111,
        follower = 22222,
        following = 33333,
        onFollowing = {},
        onFollwer = {},
        onWrite = {}
    )
}