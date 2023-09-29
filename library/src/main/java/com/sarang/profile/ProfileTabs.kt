package com.sarang.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ProfileTabs(
    isFavorite: Boolean,
    onFavorite: () -> Unit,
    onWantToGo: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.colorSecondaryLight))
    ) {
        Column(
            Modifier
                .weight(1f)
                .height(42.dp)
                .clickable { onFavorite.invoke() }
        )
        {
            Text(
                text = "즐겨찾기",
                Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .wrapContentHeight(), textAlign = TextAlign.Center
            )
            if (isFavorite)
                Spacer(
                    modifier =
                    Modifier
                        .background(colorResource(id = R.color.colorPrimary))
                        .fillMaxWidth()
                        .height(2.dp)
                )
        }
        Column(
            Modifier
                .weight(1f)
                .height(42.dp)
                .clickable { onWantToGo.invoke() }
        )
        {
            Text(
                text = "가고싶다",
                Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .wrapContentHeight(), textAlign = TextAlign.Center
            )
            if (!isFavorite)
                Spacer(
                    modifier =
                    Modifier
                        .background(colorResource(id = R.color.colorPrimary))
                        .fillMaxWidth()
                        .height(2.dp)
                )
        }
    }
}

@Preview
@Composable
fun PreviewProfileTabs() {
    ProfileTabs(
        isFavorite = true,
        onFavorite = {},
        onWantToGo = {}
    )
}
