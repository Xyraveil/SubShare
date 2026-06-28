package com.xyraveil.subshare.presentation.ui_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RatingBar(
    rating: Int,
    onRatingChanged: (Int) -> Unit
)
{
    Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {

        for (i in 1..5) {

            Icon(

                imageVector =
                        Icons.Outlined.Star,

                contentDescription = null,

                tint = if(i<=rating )Color(0xFFFFC107) else MaterialTheme.colorScheme.onSurface,

                modifier = Modifier
                    .size(48.dp)
                    .clickable {

                        onRatingChanged(i)

                    }

            )


        }

    }
}