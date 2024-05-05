package com.sarang.torang.compose.follow

import TorangAsyncImage1
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet

@Composable
internal fun ItemFollow(
    url: String,
    id: String,
    name: String,
    isMe: Boolean = false,
    onButton: (() -> Unit)? = null,
    btnText: String? = null,
    onProfile: (() -> Unit)? = null
) {
    ConstraintLayout(
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp)
            .fillMaxWidth()
            .clickable { onProfile?.invoke() }
            .height(60.dp),
        constraintSet = itemFollowConstraintSet()
    ) {
        TorangAsyncImage1(
            model = url,
            modifier = Modifier
                .layoutId("profileImg")
                .clip(CircleShape)
                .size(45.dp),
        )

        Text(text = id, modifier = Modifier.layoutId("id"))

        Text(
            text = name,
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.layoutId("name")
        )

        if (isMe) {
            Button(
                onClick = { onButton?.invoke() },
                modifier = Modifier
                    .height(35.dp)
                    .layoutId("button")
            ) {
                Text(text = btnText ?: "")
            }
        }
    }
}

fun itemFollowConstraintSet(): ConstraintSet {
    return ConstraintSet {
        val profileImg = createRefFor("profileImg")
        val id = createRefFor("id")
        val name = createRefFor("name")
        val button = createRefFor("button")

        constrain(profileImg) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
        }

        constrain(id) {
            top.linkTo(profileImg.top)
            bottom.linkTo(name.top)
            start.linkTo(profileImg.end, margin = 8.dp)
        }

        constrain(name) {
            bottom.linkTo(profileImg.bottom)
            top.linkTo(id.bottom)
            start.linkTo(profileImg.end, margin = 8.dp)
        }

        constrain(button) {
            end.linkTo(parent.end, margin = 8.dp)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
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
    )
}