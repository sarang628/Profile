package com.sarang.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    profileBaseUrl: String = "",
    profileUrl: String,
    name: String,
    feedCount: Int,
    follower: Int,
    following: Int
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column {
            AsyncImage(
                model = profileBaseUrl + profileUrl,
                contentDescription = "",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color(0x11000000)),
                contentScale = ContentScale.Crop
            )
            Text(
                text = name,
                Modifier.padding(start = 8.dp, top = 8.dp),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Row(
            Modifier
                .weight(1f)
                .padding(start = 16.dp)
        ) {
            Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally)
            {
                Text(
                    text = feedCount.toString(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = "게시물", fontSize = 18.sp)
            }

            Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally)
            {
                Text(
                    text = follower.toString(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = "팔로워", fontSize = 18.sp)
            }

            Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally)
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
        name = "",
        feedCount = 11111,
        follower = 22222,
        following = 33333
    )
}