package com.xyraveil.subshare.presentation.screen.DetailScreen

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.xyraveil.subshare.presentation.theme.lighteryellow
import com.xyraveil.subshare.presentation.theme.ligtheryellowdarkerscreen

@OptIn(ExperimentalMaterial3Api::class)

@Composable

fun FavouritesScreenTopAppBar() {
    TopAppBar(
        title = {
            Text(
                "Favourites",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                color = Color.Black
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = if(isSystemInDarkTheme()) ligtheryellowdarkerscreen else lighteryellow
        )
    )
}