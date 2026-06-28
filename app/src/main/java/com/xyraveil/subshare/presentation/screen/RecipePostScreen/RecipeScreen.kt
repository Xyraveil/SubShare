package com.xyraveil.subshare.presentation.screen.RecipePostScreen

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.xyraveil.subshare.presentation.screen.DetailScreen.RecipeScreenTopAppBar
import com.xyraveil.subshare.presentation.ui_components.BottomNavBar


@Composable

fun RecipeScreen(navController: NavController) {
    Scaffold(
        topBar = { RecipeScreenTopAppBar() },
        bottomBar = {
            BottomNavBar(navController, 1)
        },
        containerColor = MaterialTheme.colorScheme.background

    ) { innerPadding ->


        RecipeScreenBody(innerPadding,navController)
    }
}

