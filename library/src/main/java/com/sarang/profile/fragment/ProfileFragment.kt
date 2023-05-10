package com.sarang.profile.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.sarang.profile.R
import com.sarang.profile.viewmodel.ProfileViewModel
import com.sarang.profile.databinding.FragmentProfileBinding
import com.sarang.profile.uistate.ProfileUiState
import com.sarang.profile.uistate.testProfileUiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*

/**
 * [ProfileRvAdt]
 * [ProfileInfoHolder]
 * [TimeLineVH]
 * [FragmentProfileBinding]
 */

class ProfileFragment  {

}

@Composable
fun test() {

    Row() {
        Spacer(modifier = Modifier.weight(1f))
        Image(painter = painterResource(id = R.drawable.ic_settings), contentDescription = "")
    }

    Column(modifier = Modifier.padding(start = 8.dp, end = 8.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_settings),
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
                    Text(text = "0", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text(text = "게시물", fontSize = 18.sp)
                }

                Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally)
                {
                    Text(text = "0", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text(text = "팔로워", fontSize = 18.sp)
                }

                Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally)
                {
                    Text(text = "0", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text(text = "팔로잉", fontSize = 18.sp)
                }
            }

        }
        Spacer(modifier = Modifier.height(50.dp))
        Row() {
            Button(
                onClick = { }, modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
            ) {
                Text(text = "프로필 편집")
            }
        }
    }

}

@Preview
@Composable
fun previewTest() {
    test()
}