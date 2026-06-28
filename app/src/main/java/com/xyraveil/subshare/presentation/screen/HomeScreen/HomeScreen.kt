package com.xyraveil.subshare.presentation.screen.HomeScreen

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.xyraveil.subshare.data.repository.AuthRepository
import com.xyraveil.subshare.data.repository.RecipeRepository
import com.xyraveil.subshare.data.repository.UserRepository
import com.xyraveil.subshare.domain.model.Recipe
import com.xyraveil.subshare.presentation.theme.DividerColorLight
import com.xyraveil.subshare.presentation.ui_components.BottomNavBar


@Composable
fun HomeScreen(navController: NavController) {
    var selectedCategory by rememberSaveable() {
        mutableStateOf("All")
    }
    var searchQuery by rememberSaveable() {
        mutableStateOf("")
    }
    var username by rememberSaveable() { mutableStateOf("") }
    val currentUser = AuthRepository.getCurrentUser()
    LaunchedEffect(Unit) {

        if (currentUser != null) {

            UserRepository.getUser(

                uid = currentUser.uid,

                onSuccess = { user ->

                    username = user.username
                },

                onFailure = {

                }

            )

        }

    }

    val Greeting = "Hi, " + username.substringBefore(" ") + " 👋";
    Scaffold(
        bottomBar = { BottomNavBar(navController, 0) },
        containerColor = MaterialTheme.colorScheme.background

    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(innerPadding)
        )
        {
            var recipes by remember {
                mutableStateOf<List<Recipe>>(emptyList())
            }
            LaunchedEffect(Unit) {

                RecipeRepository.getRecipes(

                    onSuccess = {

                        recipes = it

                    },

                    onFailure = {

                    }

                )

            }
            val filteredRecipes = when (selectedCategory) {
                "Veg" -> recipes.filter { it.veg }
                "Non-Veg" -> recipes.filter { !it.veg }
                "Rating: High to Low" -> recipes.sortedByDescending { it.rating }
                "Rating: Low to High" -> recipes.sortedByDescending { 5 - it.rating }

                else -> recipes
            }
            val filteredBySearchRecipes =
                filteredRecipes.filter { recipe ->

                    recipe.title.contains(searchQuery, true) ||

                            recipe.protein.contains(searchQuery, true) ||

                            recipe.bread.contains(searchQuery, true) ||

                            recipe.cheese.contains(searchQuery, true) ||

                            recipe.creatorUsername.contains(searchQuery, true) ||

                            recipe.vegetables.any {
                                it.contains(searchQuery, true)
                            } ||

                            recipe.sauces.any {
                                it.contains(searchQuery, true)
                            }

                }
            ProductGrid(filteredBySearchRecipes, navController = navController)
            {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                )
                {
                    Column {
                        Text(
                            text = Greeting,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Discover and share \namazing Subway recipes",
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 16.sp,
                            lineHeight = 16.sp
                        )
                    }

//                    Icon(
//                        imageVector = Icons.Default.ArrowDropDown,
//                        tint = Color.Black,
//                        contentDescription = "Change Location",
//                    )
                }
                Spacer(modifier = Modifier.height(40.dp))

                SearchBar(searchQuery)
                {
                    searchQuery = it
                }
                Spacer(modifier = Modifier.height(20.dp))
                categories(
                    selectedCategory = selectedCategory,
                    onCategorySelected = {
                        selectedCategory = it
                    }
                )
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    color = if (isSystemInDarkTheme()) DividerColorLight else DividerColorLight
                )
            }

        }

    }
}
