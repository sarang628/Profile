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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import com.sarang.torang.compose.profile.LocalProfileImage
import com.sarang.torang.compose.profile.ProfileImageTypeData

@Preview(showBackground = true)
@Composable
internal fun ItemFollow(url             : String        = "",
                        id              : String        = "",
                        name            : String        = "",
                        showRightButton : Boolean       = false,
                        onRightButton   : () -> Unit    = {},
                        rightButtonText : String        = "",
                        onProfile       : () -> Unit    = {} ) {
    ConstraintLayout(modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                                        .fillMaxWidth()
                                        .clickable { onProfile.invoke() }
                                        .height(60.dp),
                     constraintSet = itemFollowConstraintSet()) {
        LocalProfileImage.current.invoke(
            ProfileImageTypeData( modifier = Modifier.layoutId("profileImg")
                                                          .clip(CircleShape)
                                                          .size(45.dp),
                                       url = url,
                                       errorIconSize = 20.dp,
                                       progressSize = 20.dp,
                                       contentScale = ContentScale.Crop ))

        Text(text = id, modifier = Modifier.layoutId("id"))

        Text(text = name,
             fontSize = 14.sp,
             color = Color.Gray,
             modifier = Modifier.layoutId("name") )

        if (showRightButton) {
            Button(onClick = { onRightButton.invoke() },
                   modifier = Modifier.height(35.dp)
                                      .layoutId("button")) {
                Text(text = rightButtonText)
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