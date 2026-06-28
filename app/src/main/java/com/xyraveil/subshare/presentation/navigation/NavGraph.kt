package com.xyraveil.subshare.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.xyraveil.subshare.data.repository.AuthRepository
import com.xyraveil.subshare.presentation.screen.DetailScreen.DetailScreen
import com.xyraveil.subshare.presentation.screen.FavouritesScreen.FavouritesScreen
import com.xyraveil.subshare.presentation.screen.FavouritesScreen.ProfileScreen
import com.xyraveil.subshare.presentation.screen.HomeScreen.HomeScreen
import com.xyraveil.subshare.presentation.screen.LoginScreen.LoginScreen
import com.xyraveil.subshare.presentation.screen.RecipePostScreen.RecipeScreen
import com.xyraveil.subshare.presentation.screen.RegisterScreen.RegisterScreen
import com.xyraveil.subshare.presentation.screen.welcomescreen.WelcomeScreen

@Composable
fun NavGraph()
{
    val navController = rememberNavController()
    val startDestination = if(AuthRepository.getCurrentUser() != null) Routes.HomeScreen else Routes.WelcomeScreen
    NavHost(navController = navController,startDestination = startDestination)
    {
        composable<Routes.WelcomeScreen>
        {
            WelcomeScreen(navController)
        }

        composable<Routes.HomeScreen>
        {
            HomeScreen(navController)
        }
        composable<Routes.DetailScreen>
        {
                backStackEntry ->
            val args = backStackEntry.toRoute<Routes.DetailScreen>()
            DetailScreen(recipeId = args.recipeId,navController)
        }


        composable<Routes.FavouritesScreen>
        {
            FavouritesScreen(navController)
        }
        composable<Routes.ProfileScreen>
        {
            ProfileScreen(navController)
        }
        composable<Routes.LoginScreen>
        {
            LoginScreen(navController)
        }
        composable<Routes.RegisterScreen>
        {
            RegisterScreen(navController)
        }
        composable<Routes.RecipeScreen>
        {
            RecipeScreen(navController)
        }

    }

}