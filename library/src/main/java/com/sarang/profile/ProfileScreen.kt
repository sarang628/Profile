package com.sarang.profile

import android.app.AlertDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.sarang.profile.viewmodel.ProfileViewModel
import kotlinx.coroutines.flow.MutableStateFlow


@Composable
fun _ProfileScreen(
    profileBaseUrl: String = "",
    profileViewModel: ProfileViewModel,
    onLogout: (Void?) -> Unit,
    favorite: @Composable () -> Unit,
    wantToGo: @Composable () -> Unit,
    onEditProfile: () -> Unit
) {

    val context = LocalContext.current
    val uiState by profileViewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .background(colorResource(id = com.sarang.theme.R.color.colorSecondaryLight))
            .fillMaxHeight()
    )
    {

        Row {
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.ic_settings), contentDescription = "",
                Modifier.clickable {
                    AlertDialog.Builder(context)
                        .setMessage("로그아웃 하시겠습니까?")
                        .setPositiveButton("예") { _, _ -> onLogout.invoke(null) }
                        .setNegativeButton("아니오") { _, _ -> }
                        .show()
                }
            )
        }

        Column(modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 20.dp)) {
            ProfileSummary(
                profileBaseUrl = profileBaseUrl,
                profileUrl = uiState.profileUrl,
                feedCount = uiState.feedCount,
                follower = uiState.follower,
                following = uiState.following,
                name = uiState.name
            )
            Spacer(modifier = Modifier.height(30.dp))
            Row {
                Button(
                    onClick = {
                        onEditProfile.invoke()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = com.sarang.theme.R.color.colorPrimary))
                ) {
                    Text(
                        text = "프로필 편집",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            FavoriteAndWantToGo(
                wantToGo = { wantToGo.invoke() },
                favorite = { favorite.invoke() }
            )
        }
    }
}