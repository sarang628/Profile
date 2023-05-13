package com.sarang.profile

import android.content.Context
import android.provider.ContactsContract.Profile
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.library.JsonToObjectGenerator
import com.sarang.profile.R
import com.sarang.profile.uistate.ProfileUiState


@Composable
fun ProfileScreen(uiState: ProfileUiState) {

    Box(
        modifier = Modifier
            .background(colorResource(id = R.color.colorSecondaryLight))
            .fillMaxHeight()
    )
    {

        Row() {
            Spacer(modifier = Modifier.weight(1f))
            Image(painter = painterResource(id = R.drawable.ic_settings), contentDescription = "")
        }

        Column(modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 20.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                AsyncImage(
                    model = uiState.profileUrl,
                    contentDescription = "",
                    modifier = Modifier.size(100.dp)
                )

                Row(
                    Modifier
                        .weight(1f)
                        .padding(start = 16.dp)
                ) {
                    Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally)
                    {
                        Text(
                            text = uiState.feedCount.toString(),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(text = "게시물", fontSize = 18.sp)
                    }

                    Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally)
                    {
                        Text(
                            text = uiState.follower.toString(),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(text = "팔로워", fontSize = 18.sp)
                    }

                    Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally)
                    {
                        Text(
                            text = uiState.following.toString(),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(text = "팔로잉", fontSize = 18.sp)
                    }
                }

            }
            Spacer(modifier = Modifier.height(50.dp))
            Row() {
                Button(
                    onClick = { },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.colorPrimary))
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
    }
}


@Composable
fun previewProfile(context: Context) {
    val uiState: ProfileUiState = JsonToObjectGenerator<ProfileUiState>().getObjectByFile(
        context = context,
        "profile.json",
        ProfileUiState::class.java
    )
    ProfileScreen(uiState = uiState)
}
