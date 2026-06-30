package com.xyraveil.subshare.presentation.screen.FavouritesScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.xyraveil.subshare.data.repository.AuthRepository
import com.xyraveil.subshare.data.repository.RecipeRepository
import com.xyraveil.subshare.data.repository.UserRepository
import com.xyraveil.subshare.domain.model.Recipe
import com.xyraveil.subshare.presentation.navigation.Routes
import com.xyraveil.subshare.presentation.screen.DetailScreen.ProfileScreenTopAppBar
import com.xyraveil.subshare.presentation.screen.ProfileScreen.MyRecipeCard
import com.xyraveil.subshare.presentation.ui_components.AppMessageDialogBox
import com.xyraveil.subshare.presentation.ui_components.BottomNavBar

@Composable

fun ProfileScreen(navController: NavController) {

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var loggingOut by remember { mutableStateOf(false) }

    val currentUser = AuthRepository.getCurrentUser()

    LaunchedEffect(Unit) {

        if (currentUser != null) {

            UserRepository.getUser(

                uid = currentUser.uid,

                onSuccess = { user ->

                    username = user.username
                    email = user.email

                },

                onFailure = {

                }

            )

        }

    }

    var myRecipes by remember {
        mutableStateOf<List<Recipe>>(emptyList())
    }

    LaunchedEffect(Unit) {

        val uid = AuthRepository.getCurrentUser()?.uid ?: return@LaunchedEffect
        RecipeRepository.getUserRecipes(

            uid,

            onSuccess = {

                myRecipes = it

            },

            onFailure = {}

        )

    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { ProfileScreenTopAppBar() },
        bottomBar = {
            BottomNavBar(navController, 3)
        },
        containerColor = MaterialTheme.colorScheme.background
    )
    { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 0.dp, bottom = 16.dp)
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                Box(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                            shape = CircleShape
                        )
                        .size(110.dp),
                    contentAlignment = Alignment.Center
                )
                {
                    Icon(
                        imageVector = Icons.Default.Person,
                        modifier = Modifier.size(70.dp),
                        contentDescription = "PFP",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Text(
                    text = username,
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 16.dp)
                )
                Text(
                    text = email,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 16.sp,
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(64.dp))



                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                )
                {
                    Column(modifier = Modifier.fillMaxWidth()) {

                        Row(
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .fillMaxWidth()
                                .clickable(onClick = {

                                }), verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                tint = MaterialTheme.colorScheme.primary,
                                contentDescription = "Settings",
                                modifier = Modifier
                                    .padding(bottom = 8.dp, top = 8.dp, start = 16.dp, end = 16.dp)
                                    .size(32.dp)
                            )
                            Text(
                                text = "Settings",
                                style = MaterialTheme.typography.titleLarge,
                                fontSize = 20.sp,
                                modifier = Modifier,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                        HorizontalDivider(
                            modifier = Modifier

                                .fillMaxWidth()
                                .padding(horizontal = 10.dp),
                            thickness = 2.dp
                        )
                        Row(
                            modifier = Modifier
                                .padding(bottom = 8.dp)
                                .fillMaxWidth()
                                .clickable(onClick = {
                                    loggingOut = true
                                }), verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Logout,
                                tint = MaterialTheme.colorScheme.primary,
                                contentDescription = "LogOut",
                                modifier = Modifier
                                    .padding(bottom = 8.dp, top = 8.dp, start = 20.dp, end = 12.dp)
                                    .size(32.dp)
                            )
                            Text(
                                text = "Log Out",
                                style = MaterialTheme.typography.titleLarge,
                                fontSize = 20.sp,
                                modifier = Modifier,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 1.dp, top = 16.dp, end = 1.dp),
                    thickness = 2.dp
                )
                Spacer(modifier = Modifier.height(22.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Your Blueprints",
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 24.sp,
                        modifier = Modifier,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                if (myRecipes.isEmpty()) {

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text = "❤️ Your Blueprints will \nappear here...",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center
                        )

                    }

                }

                myRecipes.forEach { recipe ->

                    MyRecipeCard(
                        recipe = recipe, navController = navController
                    )
                    {
                        myRecipes = myRecipes.filter {

                            it.recipeId != recipe.recipeId

                        }
                    }


                }
                AppMessageDialogBox(
                    show = loggingOut,
                    title = "Log Out?",
                    message = "Are you sure you want to Log out?",
                    onDismiss = { loggingOut = false },
                    confirmandDismiss = true,
                    onConfirm = {
                        AuthRepository.logout(); navController.navigate(Routes.LoginScreen) {
                        popUpTo(0) {
                            inclusive = true
                        }
                    }; loggingOut = false;
                    },
                    dismissMessage = "Cancel",
                    confirmMessage = "Log Out"
                )

            }
        }
    }
}
