package com.xyraveil.subshare.presentation.screen.DetailScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.xyraveil.subshare.domain.model.Recipe

@Composable
fun ProductDetailsBody(
    recipe: Recipe,
    innerPadding: PaddingValues
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp, bottom = 0.dp, start = 16.dp, end = 16.dp)
            .padding(innerPadding)
    )
    {

        AsyncImage(
            model = recipe.imageUrl,
            contentDescription = recipe.notes,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(
                    color = MaterialTheme.colorScheme.primary,
                    width = 2.dp,
                    shape = RoundedCornerShape(16.dp)
                ),
            contentScale = ContentScale.FillBounds
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = recipe.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            val color = if (recipe.veg) lerp(MaterialTheme.colorScheme.primary, Color.Yellow, 0.5f) else lerp(
                Color.Red,
                Color.Black,
                0.2f
            )
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .background(color = Color.White)
                ,

                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .border(
                            width = 3.dp,
                            color = color,
                            shape = RoundedCornerShape(0.dp)
                        )
                        .padding(3.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(14.dp)
                            .background(color, CircleShape)
                    )
                }
            }

        }
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = if (recipe.veg) "Veg" else "Non-Veg",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = "By " + recipe.creatorUsername,
                fontSize = 16.sp,
                color = Color.DarkGray,
                fontWeight = FontWeight.SemiBold
            )

        }
        Spacer(modifier = Modifier.height(2.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(

                text = "⭐ ${String.format("%.1f", recipe.rating)}/5 (${recipe.ratingCount})"
            )

        }
        Spacer(modifier = Modifier.height(16.dp))

        HorizontalDivider(color = Color.LightGray)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Description",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = recipe.notes,
            fontSize = 16.sp,
            fontFamily = FontFamily.Monospace,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Spacer(modifier = Modifier.height(32.dp))
        HorizontalDivider(color = Color.LightGray)
        Spacer(modifier = Modifier.height(32.dp))


/////////////////////////////////////////////////////////////////////////////Description of BluePrint


        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Bread",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(12.dp))
            cosmeticCard(
                text = recipe.bread,
                modifier = Modifier
                    .height(46.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider(color = Color.LightGray)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        )
        {
            Text(
                text = "Size: ",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(12.dp))
            cosmeticCard(
                text = if (recipe.footlong) "Footlong" else "6-inch",
                modifier = Modifier
                    .height(46.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider(color = Color.LightGray)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = "Protein:",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            cosmeticCard(
                text = recipe.protein,
                modifier = Modifier
                    .height(46.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider(color = Color.LightGray)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Cheese:",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            cosmeticCard(
                text = recipe.cheese,
                modifier = Modifier
                    .height(46.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider(color = Color.LightGray)
        Spacer(modifier = Modifier.height(8.dp))





        Text(
            text = "Veggies",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            modifier = Modifier.height(60.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            if (recipe.vegetables.isEmpty()) {
                item {
                    cosmeticCard(
                        text = "None",
                        modifier = Modifier
                            .height(46.dp)
                    )

                }
            } else
                item {
                    recipe.vegetables.forEach { text ->
                        cosmeticCard(
                            text = text,
                            modifier = Modifier
                                .height(46.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                }

        }
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider(color = Color.LightGray)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Sauces",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))
        LazyRow(
            modifier = Modifier.height(60.dp),
            horizontalArrangement = Arrangement.spacedBy(32.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            if (recipe.sauces.isEmpty()) {
                item {
                    cosmeticCard(
                        text = "None",
                        modifier = Modifier
                            .height(46.dp)
                    )
                }
            } else
                item {
                    recipe.sauces.forEach { text ->
                        cosmeticCard(
                            text = text,
                            modifier = Modifier
                                .height(46.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                }

        }
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider(color = Color.LightGray)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Seasonings",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))
        LazyRow(
            modifier = Modifier.height(60.dp),
            horizontalArrangement = Arrangement.spacedBy(32.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            if (recipe.seasonings.isEmpty()) {
                item {
                    cosmeticCard(
                        text = "None",
                        modifier = Modifier
                            .height(46.dp)
                    )
                }
            } else
                item {
                    recipe.seasonings.forEach { text ->
                        cosmeticCard(
                            text = text,
                            modifier = Modifier
                                .height(46.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                }

        }
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider(color = Color.LightGray)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text(
                text = "Toasted",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            cosmeticCard(
                text = if (recipe.toasted) "Yes" else "No",
                modifier = Modifier
                    .height(46.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider(color = Color.LightGray)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Rate this recipe",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        //Spacer(modifier = Modifier.height(8.dp))

    }


}

