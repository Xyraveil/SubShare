package com.xyraveil.subshare.presentation.screen.HomeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.xyraveil.subshare.domain.model.Recipe
import com.xyraveil.subshare.presentation.navigation.Routes
import com.xyraveil.subshare.presentation.theme.lighteryellow
import com.xyraveil.subshare.presentation.theme.ligtheryellowdarkerscreen



@Composable
fun productCard(recipe: Recipe,
                navController: NavController,
                modifier: Modifier = Modifier ) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .border(color = MaterialTheme.colorScheme.onSurface, width = 1.dp, shape = RoundedCornerShape(16.dp))
            .clickable { navController.navigate(Routes.DetailScreen(recipe.recipeId)) },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if(isSystemInDarkTheme()) ligtheryellowdarkerscreen else lighteryellow
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    )
    {
        Column(modifier = Modifier.padding(8.dp))
        {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .border(color =  MaterialTheme.colorScheme.onSurface  , width = 1.dp, shape = RoundedCornerShape(16.dp))
            ) {
                AsyncImage(
                    model = recipe.imageUrl,
                    contentDescription = recipe.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp))
                )
            }
                Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text(
                        text = recipe.title,
                        style = typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis

                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "By "+recipe.creatorUsername,
                        style = typography.bodySmall,
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                val color = if (recipe.veg) lerp(MaterialTheme.colorScheme.primary, Color.Yellow, 0.5f) else lerp(Color.Red, Color.Black, 0.2f)
                Box(modifier = Modifier
                    .size(24.dp)
                    .background(color = Color.White,shape = RoundedCornerShape(12.dp))
                    ,

                    contentAlignment = Alignment.Center) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .border(
                                width = 2.dp,
                                color = color,
                                shape = RoundedCornerShape(6.dp)
                            )
                            .padding(3.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(11.dp)
                                .background(color, CircleShape)
                        )
                    }
                }
            }
                Spacer(modifier = Modifier.height(4.dp))

//                    Text(
//                        text = "$${product.price}",
//                        style = typography.titleMedium,
//                        fontWeight = FontWeight.Bold,
//                        color = MaterialTheme.colorScheme.primary,
//                    )
//                    IconButton(
//                        onClick = {product.CartQuant++ },
//                        modifier = Modifier.background(
//                            color = MaterialTheme.colorScheme.primary,
//                            shape = RoundedCornerShape(10.dp)
//                        )
//                    ) {
//                        if(product.CartQuant==0) Icon(
//                            imageVector = Icons.Default.Add,
//                            contentDescription = "Add to Cart",
//                            tint = Color.White
//                        )
//                        else if(product.CartQuant>0) Text(
//                            text = "+${product.CartQuant}",
//                            color = Color.White
//                        )



        }
    }
}