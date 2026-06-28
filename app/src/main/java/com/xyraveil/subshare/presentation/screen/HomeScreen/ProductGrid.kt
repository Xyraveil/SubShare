package com.xyraveil.subshare.presentation.screen.HomeScreen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.xyraveil.subshare.domain.model.Recipe

@Composable

fun ProductGrid(
    recipes : List<Recipe>,
    navController : NavController,
    topContent : @Composable () -> Unit

) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
           // .padding(8.dp)

    ) {
        item{topContent()}

        items(recipes.chunked(2))
        {
            products ->
            Row(modifier = Modifier.fillMaxWidth())
            {
                if(products.size == 2)
                    {
                    productCard(products[0], modifier = Modifier.weight(1f), navController = navController)
                        Spacer(modifier = Modifier.width(16.dp))
                    productCard(products[1],modifier =Modifier.weight(1f),navController = navController)
                }
                else
                {
                    productCard(products[0], modifier = Modifier.weight(1f),navController = navController)
                    Spacer(modifier = Modifier.width(16.dp))
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}