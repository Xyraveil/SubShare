package com.xyraveil.subshare.presentation.screen.DetailScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.xyraveil.subshare.R
import com.xyraveil.subshare.data.repository.FavouriteRepository
import com.xyraveil.subshare.domain.model.Recipe
import com.xyraveil.subshare.presentation.theme.lighteryellow
import com.xyraveil.subshare.presentation.theme.ligtheryellowdarkerscreen

@OptIn(ExperimentalMaterial3Api::class)

@Composable

fun DetailScreenTopBar(recipe: Recipe, navController: NavController) {

    var isFavourite by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(recipe.recipeId) {

        FavouriteRepository.isFavourite(
            recipe.recipeId
        ) {
            isFavourite = it
        }

    }
    TopAppBar(
        title = {
            Text(
                "Details",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 32.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = if(isSystemInDarkTheme()) ligtheryellowdarkerscreen else lighteryellow),
        actions = {
            Icon(

                painter = painterResource(if (isFavourite) R.drawable.heartfilled else R.drawable.heartoutline  ),
                tint = MaterialTheme.colorScheme.onBackground ,
                contentDescription = "Add to Favorite",
                modifier = Modifier
                    .padding(end = 12.dp)
                    .size(30.dp)
                    .clickable {

                        if (isFavourite) {

                            FavouriteRepository.removeFavourite(

                                recipe.recipeId,

                                onSuccess = {
                                    isFavourite = false
                                },

                                onFailure = { }

                            )

                        } else {

                            FavouriteRepository.addFavourite(

                                recipe.recipeId,

                                onSuccess = {
                                    isFavourite = true
                                },

                                onFailure = { }

                            )

                        }

                    }
            )
        },
        navigationIcon = {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back to Previous screen",
                tint =  Color.Black,
                modifier = Modifier
                    .size(45.dp)
                    .padding(start = 12.dp)
                    .clickable(onClick = { navController.navigateUp() })
            )
        }
    )
}