package com.xyraveil.subshare.presentation.screen.DetailScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.xyraveil.subshare.data.repository.RatingRepository
import com.xyraveil.subshare.data.repository.RecipeRepository
import com.xyraveil.subshare.domain.model.Recipe
import com.xyraveil.subshare.presentation.ui_components.RatingBar

@Composable

fun DetailScreen(recipeId: String, navController: NavController) {
    var recipe by remember { mutableStateOf<Recipe?>(null) }
    var userRating by remember {
        mutableStateOf(0)
    }

    LaunchedEffect(recipeId) {

        RecipeRepository.getRecipe(

            recipeId = recipeId,

            onSuccess = {
                recipe = it
            },

            onFailure = {
                // Handle error
            }

        )
        RatingRepository.getUserRating(

            recipeId,

            onSuccess = {

                userRating = it

            },

            onFailure = { }

        )
    }
    val currentrecipe = recipe
    if (currentrecipe == null) {
        // Show loading
    } else {

        Scaffold(
            topBar = { DetailScreenTopBar(currentrecipe, navController) },
            containerColor = MaterialTheme.colorScheme.background
        ) { innerPadding ->
            LazyColumn() {
                item {
                    ProductDetailsBody(
                        currentrecipe,
                        innerPadding
                    )
                }
                item{
                    Row(modifier = Modifier.fillMaxWidth().height(100.dp),horizontalArrangement = Arrangement.Center) {
                        RatingBar(

                            rating = userRating,

                            onRatingChanged = {

                                userRating = it
                                RatingRepository.submitRating(
                                    recipeId = currentrecipe.recipeId,
                                    rating = userRating ,
                                    onSuccess = {
                                        RecipeRepository.getRecipe(
                                            recipeId = currentrecipe.recipeId,
                                            onSuccess = {
                                                recipe = it
                                            },
                                            onFailure = { }
                                        )
                                    },
                                    onFailure = {
                                        // Handle error
                                    }
                                )
                            }

                        )
                    }
                }
            }
        }
    }
}