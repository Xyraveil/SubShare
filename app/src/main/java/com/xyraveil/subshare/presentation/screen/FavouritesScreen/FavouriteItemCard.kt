package com.xyraveil.subshare.presentation.screen.FavouritesScreen


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.xyraveil.subshare.domain.model.Recipe
import com.xyraveil.subshare.presentation.navigation.Routes
import com.xyraveil.subshare.presentation.ui_components.AppMessageDialogBox

@Composable
fun FavouriteItemCard(
    recipe: Recipe,
    navController: NavController,
    onDelete: (Recipe) -> Unit
) {
    var deleteFav by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .border(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f), width = 2.dp, shape = RoundedCornerShape(12.dp))
            .clickable(onClick = { navController.navigate(Routes.DetailScreen(recipe.recipeId)) }),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = Color.Transparent
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .background(color = Color.Transparent),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Box(modifier = Modifier
                .clip(shape = RoundedCornerShape(12.dp))
                .size(100.dp)
                .border(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f), width = 2.dp, shape = RoundedCornerShape(12.dp))){

                AsyncImage(
                    model = recipe.imageUrl,
                    contentDescription = "Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Column(modifier = Modifier.padding(horizontal = 16.dp).width(150.dp)) {
                Text(
                    text = recipe.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(text = recipe.notes, fontSize = 14.sp, color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                    )
                Text(text = "By " +recipe.creatorUsername, fontSize = 14.sp, color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            )
            {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = lerp(Color.Red, Color.Black, 0.2f),
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .size(36.dp)
                        .clickable(onClick = {deleteFav  = true})
                )
            }



        }
        AppMessageDialogBox(
            show = deleteFav ,
            title = "Remove from Favourites?",
            message =  "Are you sure you want to remove this recipe from your favourites? ",
            onDismiss = {
                deleteFav = false
            },
            confirmandDismiss = true,
            onConfirm = {
                onDelete(recipe)
                deleteFav = false
            }
        )
    }
}
