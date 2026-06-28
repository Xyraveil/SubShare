package com.xyraveil.subshare.presentation.ui_components

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.xyraveil.subshare.R
import com.xyraveil.subshare.presentation.navigation.Routes


data class NavItem(
    val title:String,
    val icon:Int,
    val routes:Routes
)
@Composable
fun BottomNavBar(navController: NavController,currentIndex:Int)
{
    val navItems = listOf(
        NavItem("Home",  R.drawable.regular_outline_home,Routes.HomeScreen),
        NavItem("Post",R.drawable.posticon, Routes.RecipeScreen),
        NavItem("Favourites",R.drawable.regular_outline_heart,Routes.FavouritesScreen),
        NavItem("Profile",R.drawable.outline_account_circle_24,Routes.ProfileScreen)
    )
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.height(100.dp)
    ) {
        var selectedIndex by remember {
            mutableStateOf(currentIndex)
        }
        navItems.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.title
                    )
                },
                label = { Text(text = item.title) },
                selected = selectedIndex == index,
                onClick = { selectedIndex = index;
                          navController.navigate(item.routes)
                          {
                              popUpTo(navController.graph.startDestinationId)
                              {
                                  saveState = true
                              }
                              launchSingleTop = true
                              restoreState = true
                          }

                          },
                alwaysShowLabel = false,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurface,
                    indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                )
            )
        }

    }
}

