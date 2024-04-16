package com.sarang.torang.compose.edit

import TorangAsyncImage
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.torang.R
import com.sarang.torang.ProfileUiState
import com.sarang.torang.viewmodel.ProfileViewModel
import kotlinx.coroutines.launch

@Composable
fun EditProfileScreen(
    profileViewModel: ProfileViewModel = hiltViewModel(),   // 프로필 뷰모델
    onEditImage: () -> Unit,                                // 프로필 수정 클릭
    onBack: () -> Unit                                      // 뒤로가기 클릭
) {
    val coroutine = rememberCoroutineScope()
    val uiState by profileViewModel.uiState.collectAsState()
    LaunchedEffect(key1 = (uiState as ProfileUiState.Success).name, block = {
        coroutine.launch {
            profileViewModel.loadProfileByToken()
        }
    })
    _EditProfileScreen(
        onEditImage = onEditImage,
        onBack = onBack,
        uiState = (uiState as ProfileUiState.Success)
    )
}

@Composable
fun _EditProfileScreen(
    onEditImage: () -> Unit,
    onBack: () -> Unit,
    uiState: ProfileUiState
) {
    Column(
        Modifier
            .fillMaxSize()
    ) {
        //titlebar
        Box(
            Modifier
                .padding(start = 8.dp, end = 8.dp)
                .height(50.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "",
                Modifier
                    .size(30.dp)
                    .align(Alignment.CenterStart)
                    .clickable {
                        onBack.invoke()
                    }
            )
            Text(
                text = "Edit profile", modifier = Modifier
                    .align(Alignment.Center), fontSize = 16.sp, fontWeight = FontWeight.Bold
            )
        }

        Text(
            text = "",
            Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(Color.LightGray)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // profile image
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TorangAsyncImage(
                model = (uiState as ProfileUiState.Success).profileUrl,
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape)
                    .background(Color(0x11000000)),
                //contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Edit picture",
                fontWeight = FontWeight.Bold,
                color = Color(0xFF4396F6),
                modifier = Modifier.clickable { onEditImage.invoke() })
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "",
            Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(Color.LightGray)
        )

        Column(Modifier.padding(start = 8.dp, end = 8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.height(45.dp)) {
                Text(text = "Name", modifier = Modifier.weight(2f))
                Box(
                    modifier = Modifier
                        .weight(8f)
                        .fillMaxHeight()
                ) {
                    Text(text = (uiState as ProfileUiState.Success).name, modifier = Modifier.align(Alignment.CenterStart))
                    Text(
                        text = "",
                        Modifier
                            .height(1.dp)
                            .fillMaxWidth()
                            .background(Color.LightGray)
                            .align(Alignment.BottomStart)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewEditProfileScreen() {
    _EditProfileScreen(
        onEditImage = { /*TODO*/ },
        onBack = { /*TODO*/ },
        uiState = ProfileUiState.Success(
            profileUrl = "",
            follower = 0,
            following = 0,
            feedCount = 0,
            name = "name",
            id = 0
        )
    )
}