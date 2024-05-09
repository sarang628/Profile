package com.sarang.torang.compose.follow

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
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
    onProfile: (() -> Unit)? = null,
    image: @Composable (Modifier, String, Dp?, Dp?, ContentScale?) -> Unit,
) {
    ConstraintLayout(
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp)
            .fillMaxWidth()
            .clickable { onProfile?.invoke() }
            .height(60.dp),
        constraintSet = itemFollowConstraintSet()
    ) {
        image.invoke(
            Modifier
                .layoutId("profileImg")
                .clip(CircleShape)
                .size(45.dp),
            url,
            20.dp,
            20.dp,
            ContentScale.Crop
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
        image = { _, _, _, _, _ -> }
    )
}