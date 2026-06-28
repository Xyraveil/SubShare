package com.xyraveil.subshare.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    object HomeScreen : Routes()
    @Serializable
    data class DetailScreen(val recipeId: String) : Routes()
    @Serializable
    object WelcomeScreen : Routes()

    @Serializable
    object CartScreen : Routes()
    @Serializable
    object ProfileScreen : Routes()
    @Serializable
    object FavouritesScreen : Routes()
    @Serializable
    object LoginScreen : Routes()

    @Serializable
    object RegisterScreen : Routes()

    @Serializable
    object RecipeScreen : Routes()


}
