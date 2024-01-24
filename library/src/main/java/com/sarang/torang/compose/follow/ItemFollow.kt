package com.sarang.torang.compose.follow

import TorangAsyncImage
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ItemFollow(
    url: String,
    id: String,
    name: String,
    onFollow: () -> Unit,
    isFollower: Boolean,
    onDelete: () -> Unit,
    onUnFollow: () -> Unit
) {
    Row(
        Modifier
            .padding(start = 8.dp, end = 8.dp)
            .height(60.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TorangAsyncImage(
            model = url,
            modifier = Modifier
                .clip(CircleShape)
                .size(45.dp),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(Modifier.weight(1f)) {
            Text(text = id)
            Text(
                text = name,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
        Button(
            onClick = { if (isFollower) onDelete.invoke() else onUnFollow.invoke() },
            modifier = Modifier.height(35.dp)
        ) {
            Text(text = if (isFollower) "Delete" else "Following")
        }
    }
}

@Preview
@Composable
fun PreviewItemFollow() {
    ItemFollow(
        name = "LISA",
        id = "lalalalisa_m",
        url = "url",
        onFollow = {},
        isFollower = true,
        onUnFollow = {},
        onDelete = {}
    )
}