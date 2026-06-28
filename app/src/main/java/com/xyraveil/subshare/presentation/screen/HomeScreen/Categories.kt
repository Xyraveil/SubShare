package com.xyraveil.subshare.presentation.screen.HomeScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun categories(selectedCategory: String,
               onCategorySelected: (String) -> Unit) {
    val Categories = listOf("All","Non-Veg", "Veg","Rating: High to Low","Rating: Low to High")

    LazyRow(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(Categories.size){
         category ->
            CategoryCard(
                text = Categories[category],
                isSelected = Categories[category] == selectedCategory,
                onSelected = { onCategorySelected(Categories[category]) }
            )
        }

    }
}