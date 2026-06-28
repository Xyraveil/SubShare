package com.xyraveil.subshare.presentation.screen.FavouritesScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.xyraveil.subshare.data.repository.FavouriteRepository
import com.xyraveil.subshare.data.repository.RecipeRepository
import com.xyraveil.subshare.domain.model.Recipe
import com.xyraveil.subshare.presentation.screen.DetailScreen.FavouritesScreenTopAppBar
import com.xyraveil.subshare.presentation.ui_components.BottomNavBar


@Composable

fun FavouritesScreen(navController: NavController)
{
    var favouriteRecipes by remember {
        mutableStateOf<List<Recipe>>(emptyList())
    }
    LaunchedEffect(Unit) {

        FavouriteRepository.getFavourites(

            onSuccess = { favouriteIds ->

                RecipeRepository.getRecipesByIds(

                    recipeIds = favouriteIds,

                    onSuccess = {
                        favouriteRecipes = it
                    },

                    onFailure = { }

                )

            },

            onFailure = { }

        )

    }
    Scaffold(modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { FavouritesScreenTopAppBar() },
        bottomBar = { BottomNavBar(navController,2) }
    ) {
        innerPadding ->
        if (favouriteRecipes.isEmpty()) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = "❤️ No favourite recipes yet",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

            }

        }
        LazyColumn(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                .padding(innerPadding)
        ) {

            items(favouriteRecipes) { recipe ->

                FavouriteItemCard(
                    recipe = recipe,
                    navController = navController
                )
                {
                    FavouriteRepository.removeFavourite(
                        recipeId = it.recipeId,
                        onSuccess = {

                            favouriteRecipes =
                                favouriteRecipes.filter { r ->
                                    r.recipeId != it.recipeId
                                }

                        },
                        onFailure = { }

                    )

                }

            }

        }

    }
}