package com.sarang.torang.compose.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet

@Composable
internal fun ProfileSummary(
    profileUrl: String,         // 프로필 이미지 url
    name: String,               // 이름
    feedCount: Int,             // 피드 수
    follower: Int,              // 팔로워 수
    following: Int,             // 팔로잉 수
    onFollowing: () -> Unit,    // 팔로잉 클릭
    onFollwer: () -> Unit,      // 팔로워 클릭
    onWrite: () -> Unit,         // 게시글 클릭
    image: @Composable (Modifier, String, Dp?, Dp?, ContentScale?) -> Unit,
) {

    ConstraintLayout(
        constraintSet = profileSummaryConstraintSet()
    ) {
        Box(modifier = Modifier.layoutId("profileImg"))
        {
            if (profileUrl.isNotEmpty()) {
                image.invoke(
                    Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(Color(0x11000000)),
                    profileUrl,
                    50.dp,
                    50.dp,
                    ContentScale.Crop
                )
            } else {
                Icon(
                    imageVector = Icons.Outlined.AccountCircle,
                    contentDescription = "emptyProfile",
                    modifier = Modifier.size(100.dp),
                )
            }
        }
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column {
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
                        onFollwer.invoke()
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

internal fun profileSummaryConstraintSet(): ConstraintSet {
    return ConstraintSet {
        val contents = createRefFor("contents")
    }
}

@Preview
@Composable
fun PreviewProfileSummary() {
    ProfileSummary(/*Preview*/
        profileUrl = "",
        name = "name",
        feedCount = 11111,
        follower = 22222,
        following = 33333,
        onFollowing = {},
        onFollwer = {},
        onWrite = {},
        image = { _, _, _, _, _ -> }
    )
}