package com.sarang.torang.compose.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sarang.torang.compose.LocalProfileImage
import com.sarang.torang.compose.ProfileImageTypeData

@Preview(showBackground = true)
@Composable
internal fun ProfileSummary(
    profileUrl  : String        = "",
    name        : String        = "name",
    feedCount   : String        = "0",
    follower    : String        = "0",
    following   : String        = "0",
    onFollowing : () -> Unit    = {},
    onFollwer   : () -> Unit    = {},
    onWrite     : () -> Unit    = {},
) {
    val profileImage = @Composable {
        Box(modifier = Modifier.layoutId("profileImg")) {
            if (profileUrl.isNotEmpty()) {
                LocalProfileImage.current.invoke(
                    ProfileImageTypeData(modifier = Modifier.size(80.dp)
                                                                 .clip(CircleShape)
                                                                 .background(Color(0x11000000)),
                                              url = profileUrl,
                                              errorIconSize = 50.dp,
                                              progressSize = 50.dp,
                                              contentScale = ContentScale.Crop)) }
            else {
                Icon(imageVector = Icons.Rounded.AccountCircle,
                     contentDescription = "emptyProfile",
                     modifier = Modifier.size(90.dp))
            }
        }
    }

    fun follow(modifier : Modifier = Modifier) = @Composable {
        Row(modifier              = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {
            Column(modifier            = Modifier.clickable { onWrite.invoke() },
                   horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text       = feedCount.toString(),
                     fontSize   = 14.sp,
                     fontWeight = FontWeight.Bold)
                Text(text = "게시물", fontSize = 14.sp)
            }

            Column(modifier            = Modifier.clickable { onFollwer.invoke() },
                   horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text       = follower.toString(),
                     fontSize   = 14.sp,
                     fontWeight = FontWeight.Bold)
                Text(text = "팔로워", fontSize = 14.sp)
            }

            Column(
                Modifier.clickable {
                    onFollowing.invoke()
                }, horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Text(
                    text = following.toString(),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                Text(text = "팔로잉", fontSize = 14.sp)
            }
        }
    }
        Row(modifier = Modifier.fillMaxWidth().height(100.dp),
            verticalAlignment = Alignment.CenterVertically){
            profileImage()
            Box(Modifier.fillMaxSize().padding(vertical = 4.dp, horizontal = 20.dp)) {
                Text(text       = name,
                    fontSize   = 12.sp,
                    fontWeight = FontWeight.Bold)
                follow(modifier = Modifier.align(Alignment.Center)).invoke()
            }
    }
}