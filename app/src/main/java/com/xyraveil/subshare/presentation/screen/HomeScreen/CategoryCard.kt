package com.xyraveil.subshare.presentation.screen.HomeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xyraveil.subshare.presentation.theme.DividerColorDark
import com.xyraveil.subshare.presentation.theme.DividerColorLight


@Composable
fun CategoryCard(
    text: String,
    text2: String = "",
    isSelected: Boolean,
    onSelected: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(40.dp)
            .border(color = if(isSystemInDarkTheme()) DividerColorDark  else DividerColorLight, width = 1.5.dp, shape = RoundedCornerShape(5.dp))
            .clip(RoundedCornerShape(5.dp))
            .clickable { onSelected() }

            .background(color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    )
    {

            Text(
                text = text,
                color = if (isSelected) Color.White else MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                maxLines = 1
            )

    }
}