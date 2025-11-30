package com.sarang.torang.compose.edit

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.torang.MyProfileUiState
import com.sarang.torang.ProfileUiState
import com.sarang.torang.compose.LocalProfileImage
import com.sarang.torang.compose.ProfileImageTypeData
import com.sarang.torang.viewmodel.MyProfileViewModel

@Composable
fun EditProfileScreen(uiState           : MyProfileUiState,
                      onEditImage       : () -> Unit = {},
                      onBack            : () -> Unit = {},
) {
    when(uiState){
        is MyProfileUiState.Success ->{
            EditProfileScreen(onEditImage = onEditImage,
                onBack     = onBack,
                profileUrl = uiState.profileUrl,
                name       = uiState.name)
        }
        else -> {}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun EditProfileScreen(onEditImage  : () -> Unit     = {},
                       onBack       : () -> Unit     = {},
                       profileUrl   : String         = "",
                       name         : String         = "") {
    Scaffold(topBar = { TopAppBar(title = { Text(text = "Edit profile",
                                                 fontSize = 16.sp,
                                                 fontWeight = FontWeight.Bold)},
                      navigationIcon = { IconButton(onBack) { Icon(imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                                                            contentDescription = null) } })
    }) {
        Column(modifier = Modifier.fillMaxSize()
                                  .padding(it) ) {
            HorizontalDivider(color = Color.LightGray)
            Spacer(modifier = Modifier.height(12.dp))

            Column(modifier = Modifier.fillMaxWidth(),
                   horizontalAlignment = Alignment.CenterHorizontally) {
                LocalProfileImage.current.invoke(
                    ProfileImageTypeData(modifier = Modifier.size(70.dp)
                                                                 .clip(CircleShape)
                                                                 .background(Color(0x11000000)),
                                              url = profileUrl,
                                              errorIconSize = 30.dp,
                                              progressSize = 30.dp,
                                              contentScale = ContentScale.Crop))

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Edit picture",
                     fontWeight = FontWeight.Bold,
                     color = Color(0xFF4396F6),
                     modifier = Modifier.clickable { onEditImage.invoke() })
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = Color.LightGray,)

            Column(Modifier.padding(start = 8.dp, end = 8.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.height(45.dp)) {
                    Text(text = "Name",
                        modifier = Modifier.weight(2f))
                    Column(modifier = Modifier.weight(8f)) {
                        Box(modifier = Modifier.fillMaxHeight()){
                            Text(modifier = Modifier.align(alignment = Alignment.Center),
                                 text = name)
                        }
                        HorizontalDivider(color = Color.LightGray)
                    }
                }
            }
        }
    }
}