package com.xyraveil.subshare.presentation.screen.RecipePostScreen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.firebase.firestore.FirebaseFirestore
import com.xyraveil.subshare.data.repository.AuthRepository
import com.xyraveil.subshare.data.repository.ImageRepository
import com.xyraveil.subshare.data.repository.RecipeRepository
import com.xyraveil.subshare.data.repository.UserRepository
import com.xyraveil.subshare.domain.model.Recipe
import com.xyraveil.subshare.presentation.navigation.Routes
import com.xyraveil.subshare.presentation.ui_components.AppMessageDialogBox
import com.xyraveil.subshare.presentation.ui_components.Constants.DEFAULT_RECIPE_IMAGE

@Composable

fun RecipeScreenBody(innerPadding: PaddingValues, navController: NavController) {
    val context = LocalContext.current
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    var uploading by remember {
        mutableStateOf(false)
    }
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        imageUri = uri
    }
    var posted by remember { mutableStateOf(false) }
    var lastRecipe by remember { mutableStateOf<String>("") }
    var RecipeName by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var selectedBread by remember {
        mutableStateOf("Italian White")
    }
    var selectedProtein by remember {
        mutableStateOf("Veg Patty")
    }
    val breads = listOf(
        "Roasted Garlic",
        "Italian White",
        "Italian Herbs & Cheese",
        "Multigrain",
        "Flatbread"
    )
    val cheeses = listOf(
        "None",
        "American",
        "Cheddar",
        "Mozzarella"
    )
    val proteins = listOf(
        "Veg Patty",
        "Paneer Tikka",
        "Aloo Patty",
        "Chicken Tikka",
        "Chicken Teriyaki",
        "Roasted Chicken",
        "Chicken Slice",
        "Chicken Meatball",
        "Chicken Keema",
        "Tuna"
    )
    val vegetables = listOf(
        "Lettuce",
        "Tomato",
        "Onion",
        "Cucumber",
        "Capsicum",
        "Jalapeños",
        "Pickles",
        "Black Olives"
    )
    val sauces = listOf(
        "Mayonnaise",
        "Mint Mayo",
        "Chipotle Southwest",
        "Honey Mustard",
        "Sweet Onion",
        "Barbecue",
        "Mustard",
        "Thousand Island",
        "Schezwan",
        "Marinara",
        "Sriracha"
    )
    val seasonings = listOf(
        "Salt",
        "Black Pepper",
        "Oregano",
        "Chilli Flakes"
    )
    var footLong by remember { mutableStateOf(false) }
    var toasted by remember { mutableStateOf(false) }
    var selectedVegetables by remember {
        mutableStateOf(setOf<String>())
    }
    var selectedCheese by remember {
        mutableStateOf("American")
    }
    var selectedSauces by remember {
        mutableStateOf(setOf<String>())
    }
    var selectedSeasonings by remember {
        mutableStateOf(setOf<String>())
    }

    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        focusManager.clearFocus()
                    }
                )
            }
    )
    {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(innerPadding)
        )
        {
            item {
                Row {
                    Text(
                        text = "ADD A PHOTO",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "(optional)",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 18.sp,
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Card(modifier = Modifier.fillMaxWidth())
                {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(2.dp)
                            .clickable(onClick = {
                                launcher.launch(
                                    PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                    )
                                )
                            }),
                        contentAlignment = Alignment.Center

                    )
                    {
                        if (imageUri != null) {

                            AsyncImage(
                                model = imageUri,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(16.dp)),
                                contentScale = ContentScale.Crop,

                            )

                        }
                        Icon(
                            imageVector = Icons.Default.AddCircleOutline,
                            contentDescription = "Add Photo ",
                            tint = MaterialTheme.colorScheme.background.copy(alpha = 0.25f),
                            modifier = Modifier.size(150.dp)

                        )

                    }



                }
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
                Text(
                    text = "1.RECIPE NAME",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                )
                OutlinedTextField(
                    value = RecipeName,
                    onValueChange = { RecipeName = it },
                    placeholder = { Text(text = "Recipe Name",color = MaterialTheme.colorScheme.onBackground) },
                    label = { },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 1,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedLabelColor = MaterialTheme.colorScheme.onBackground  ,
                        unfocusedTextColor = MaterialTheme.colorScheme.onBackground ,
                        focusedLabelColor = MaterialTheme.colorScheme.onBackground ,
                        focusedTextColor = MaterialTheme.colorScheme.onBackground ,
                        cursorColor = MaterialTheme.colorScheme.onBackground,
                        focusedBorderColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,
                        errorBorderColor = MaterialTheme.colorScheme.primary,
                        errorCursorColor = MaterialTheme.colorScheme.primary,
                        errorLabelColor = MaterialTheme.colorScheme.primary,
                        errorTextColor = MaterialTheme.colorScheme.primary,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = MaterialTheme.colorScheme.background,

                        )
                )
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
                Row {
                    Text(
                        text = "2.BREAD ",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                    )
                    Text(
                        text = "(Choose 1)",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 18.sp,
                    )
                }
                LazyRow(modifier = Modifier.fillMaxWidth()) {
                    item {
                        breads.forEach { bread ->

                            Card(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .height(90.dp)
                                    .border(
                                        shape = RoundedCornerShape(12.dp),
                                        width = 2.dp,
                                        color = if (selectedBread == bread) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                    ),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.background,
                                    contentColor = if (selectedBread == bread) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                )
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(12.dp)
                                        .clickable {
                                            selectedBread = bread
                                        },
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(10.dp)
                                ) {

                                    RadioButton(
                                        selected = selectedBread == bread,
                                        onClick = null      // Let the Row handle the click
                                        , colors = RadioButtonDefaults.colors(
                                            selectedColor = MaterialTheme.colorScheme.primary,
                                            unselectedColor = MaterialTheme.colorScheme.onSurface
                                        )
                                    )

                                    Text(
                                        bread,
                                        fontWeight = FontWeight.SemiBold,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                }
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
                Text(
                    text = "3.SIZE",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                )
                Row(modifier = Modifier.fillMaxWidth())
                {
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .height(60.dp)
                            .width(120.dp)
                            .weight(1f)
                            .border(
                                shape = RoundedCornerShape(12.dp),
                                width = 2.dp,
                                color = if (!footLong) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                            ),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.background,
                            contentColor = if (!footLong) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp)
                                .clickable {
                                    footLong = false
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            RadioButton(
                                selected = !footLong,
                                onClick = null,    // Let the Row handle the click
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = MaterialTheme.colorScheme.primary,
                                    unselectedColor = MaterialTheme.colorScheme.onSurface
                                )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("6-inch", fontWeight = FontWeight.SemiBold)
                        }
                    }
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .height(60.dp)
                            .width(120.dp)
                            .weight(1f)
                            .border(
                                shape = RoundedCornerShape(12.dp),
                                width = 2.dp,
                                color = if (footLong) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                            ),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.background,
                            contentColor = if (footLong) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp)
                                .clickable {
                                    footLong = true
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            RadioButton(
                                selected = footLong,
                                onClick = null,    // Let the Row handle the click
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = MaterialTheme.colorScheme.primary,
                                    unselectedColor = MaterialTheme.colorScheme.onSurface
                                )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Footlong", fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
                Row {
                    Text(
                        text = "4.PROTEIN ",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                    )
                    Text(
                        text = "(Choose 1)",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 18.sp,
                    )
                }
                LazyRow(modifier = Modifier.fillMaxWidth()) {
                    item {
                        proteins.forEach { protein ->

                            Card(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .height(90.dp)
                                    .clickable {
                                        selectedProtein = protein
                                    }
                                    .border(
                                        shape = RoundedCornerShape(12.dp),
                                        width = 2.dp,
                                        color = if (selectedProtein == protein) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                    ),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.background,
                                    contentColor = if (selectedProtein == protein) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                )
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(12.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(10.dp)
                                ) {

                                    RadioButton(
                                        selected = selectedProtein == protein,
                                        onClick = null      // Let the Row handle the click
                                        , colors = RadioButtonDefaults.colors(
                                            selectedColor = MaterialTheme.colorScheme.primary,
                                            unselectedColor = MaterialTheme.colorScheme.onSurface
                                        )
                                    )

                                    Text(
                                        protein,
                                        fontWeight = FontWeight.SemiBold,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                }
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
                Row {
                    Text(
                        text = "5.CHEESE ",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                    )
                    Text(
                        text = "(Choose 1)",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 18.sp,
                    )
                }
                LazyRow(modifier = Modifier.fillMaxWidth()) {
                    item {
                        cheeses.forEach { cheese ->

                            Card(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .height(90.dp)
                                    .width(120.dp)
                                    .clickable {
                                        selectedCheese = cheese
                                    }
                                    .border(
                                        shape = RoundedCornerShape(12.dp),
                                        width = 2.dp,
                                        color = if (selectedCheese == cheese) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                    ),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.background,
                                    contentColor = if (selectedCheese == cheese) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                )
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(12.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(10.dp)
                                ) {

                                    RadioButton(
                                        selected = selectedCheese == cheese,
                                        onClick = null      // Let the Row handle the click
                                        , colors = RadioButtonDefaults.colors(
                                            selectedColor = MaterialTheme.colorScheme.primary,
                                            unselectedColor = MaterialTheme.colorScheme.onSurface
                                        )
                                    )

                                    Text(
                                        cheese,
                                        fontWeight = FontWeight.SemiBold,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                }
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
                Row {
                    Text(
                        text = "6.VEGGIES ",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                    )
                    Text(
                        text = "(Choose any)",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 18.sp,
                    )
                }
                Column {

                    vegetables.chunked(2).forEach { rowItems ->

                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            rowItems.forEach { vegetable ->

                                Row(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(60.dp)
                                            .border(
                                                shape = RoundedCornerShape(12.dp),
                                                width = 2.dp,
                                                color = if (vegetable in selectedVegetables) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                            )
                                            .clickable(onClick = {
                                                selectedVegetables =
                                                    if (vegetable in selectedVegetables)
                                                        selectedVegetables - vegetable
                                                    else
                                                        selectedVegetables + vegetable
                                            }),
                                        colors = CardDefaults.cardColors(
                                            containerColor = MaterialTheme.colorScheme.background,
                                            contentColor = if (vegetable in selectedVegetables) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                        ),
                                    )
                                    {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(16.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        )
                                        {
                                            Checkbox(
                                                checked = vegetable in selectedVegetables,
                                                onCheckedChange =
                                                    null,
                                                colors = CheckboxDefaults.colors(
                                                    checkedColor = MaterialTheme.colorScheme.primary,
                                                    uncheckedColor = MaterialTheme.colorScheme.onSurface,
                                                    checkmarkColor = MaterialTheme.colorScheme.background
                                                )
                                            )

                                            Text(
                                                vegetable,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(end = 12.dp),
                                                fontWeight = FontWeight.SemiBold,
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    }

                                }
                            }

                            // Fill empty spaces in the last row
                            repeat(2 - rowItems.size) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
                Row {
                    Text(
                        text = "7.SAUCES ",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                    )
                    Text(
                        text = "(Choose any)",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 18.sp,
                    )
                }
                Column {

                    sauces.chunked(2).forEach { rowItems ->

                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            rowItems.forEach { sauce ->

                                Row(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(60.dp)
                                            .border(
                                                shape = RoundedCornerShape(12.dp),
                                                width = 2.dp,
                                                color = if (sauce in selectedSauces) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                            )
                                            .clickable(onClick = {
                                                selectedSauces =
                                                    if (sauce in selectedSauces)
                                                        selectedSauces - sauce
                                                    else
                                                        selectedSauces + sauce
                                            }),
                                        colors = CardDefaults.cardColors(
                                            containerColor = MaterialTheme.colorScheme.background,
                                            contentColor = if (sauce in selectedSauces) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                        ),
                                    )
                                    {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(16.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        )
                                        {
                                            Checkbox(
                                                checked = sauce in selectedSauces,
                                                onCheckedChange =
                                                    null,
                                                colors = CheckboxDefaults.colors(
                                                    checkedColor = MaterialTheme.colorScheme.primary,
                                                    uncheckedColor = MaterialTheme.colorScheme.onSurface,
                                                    checkmarkColor = MaterialTheme.colorScheme.background
                                                )
                                            )

                                            Text(
                                                sauce,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(end = 12.dp),
                                                fontWeight = FontWeight.SemiBold,
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    }

                                }
                            }

                            // Fill empty spaces in the last row
                            repeat(2 - rowItems.size) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
                Row {
                    Text(
                        text = "8.SEASONINGS ",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                    )
                    Text(
                        text = "(Choose any)",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 18.sp,
                    )
                }
                Column {

                    seasonings.chunked(2).forEach { rowItems ->

                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            rowItems.forEach { seasoning ->

                                Row(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(60.dp)
                                            .border(
                                                shape = RoundedCornerShape(12.dp),
                                                width = 2.dp,
                                                color = if (seasoning in selectedSeasonings) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                            )
                                            .clickable(onClick = {
                                                selectedSeasonings =
                                                    if (seasoning in selectedSeasonings)
                                                        selectedSeasonings - seasoning
                                                    else
                                                        selectedSeasonings + seasoning
                                            }),
                                        colors = CardDefaults.cardColors(
                                            containerColor = MaterialTheme.colorScheme.background,
                                            contentColor = if (seasoning in selectedSeasonings) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                        ),
                                    )
                                    {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(16.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        )
                                        {
                                            Checkbox(
                                                checked = seasoning in selectedSeasonings,
                                                onCheckedChange =
                                                    null,
                                                colors = CheckboxDefaults.colors(
                                                    checkedColor = MaterialTheme.colorScheme.primary,
                                                    uncheckedColor = MaterialTheme.colorScheme.onSurface,
                                                    checkmarkColor = MaterialTheme.colorScheme.background
                                                )
                                            )

                                            Text(
                                                seasoning,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(end = 12.dp),
                                                fontWeight = FontWeight.SemiBold,
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    }

                                }
                            }

                            // Fill empty spaces in the last row
                            repeat(2 - rowItems.size) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
                Text(
                    text = "8.TOASTED",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold,
                )
                Row(modifier = Modifier.fillMaxWidth())
                {
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .height(60.dp)
                            .width(120.dp)
                            .weight(1f)
                            .border(
                                shape = RoundedCornerShape(64.dp),
                                width = 2.dp,
                                color = if (toasted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                            ),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.background,
                            contentColor = if (toasted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp)
                                .clickable {
                                    toasted = true
                                },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text("Yes", fontWeight = FontWeight.SemiBold)
                        }
                    }
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .height(60.dp)
                            .width(120.dp)
                            .weight(1f)
                            .border(
                                shape = RoundedCornerShape(64.dp),
                                width = 2.dp,
                                color = if (!toasted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                            ),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.background,
                            contentColor = if (!toasted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp)
                                .clickable {
                                    toasted = false
                                },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text("No", fontWeight = FontWeight.SemiBold)
                        }

                    }
                }
                HorizontalDivider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
                Spacer(modifier = Modifier.height(32.dp))
                Row {
                    Text(
                        text = "NOTES ",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                    )
                    Text(
                        text = "(optional)",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 18.sp,
                    )
                }
                OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    placeholder = { Text(text = "Description",color = MaterialTheme.colorScheme.onBackground) },
                    label = { },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 10,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedLabelColor = MaterialTheme.colorScheme.onBackground  ,
                        unfocusedTextColor = MaterialTheme.colorScheme.onBackground ,
                        focusedLabelColor = MaterialTheme.colorScheme.onBackground ,
                        focusedTextColor = MaterialTheme.colorScheme.onBackground ,
                        cursorColor = MaterialTheme.colorScheme.onBackground,
                        focusedBorderColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,
                        errorBorderColor = MaterialTheme.colorScheme.primary,
                        errorCursorColor = MaterialTheme.colorScheme.primary,
                        errorLabelColor = MaterialTheme.colorScheme.primary,
                        errorTextColor = MaterialTheme.colorScheme.primary,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = MaterialTheme.colorScheme.background,

                        )
                )
                Spacer(modifier = Modifier.height(32.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            val recipeId = FirebaseFirestore.getInstance()
                                .collection("recipes")
                                .document()
                                .id
                            val currentUser = AuthRepository.getCurrentUser()
                            val vegProteins = setOf(
                                "Veg Patty",
                                "Paneer Tikka",
                                "Aloo Patty"
                            )
                            val isVEG = selectedProtein in vegProteins
                            if (currentUser == null) {
                                return@Button
                            }
                            UserRepository.getUser(
                                currentUser.uid,
                                onSuccess = { user ->
                                    uploading = true
                                    if (imageUri == null) {

                                        val recipe = Recipe(

                                            recipeId = recipeId,

                                            title = RecipeName,

                                            bread = selectedBread,

                                            protein = selectedProtein,

                                            cheese = selectedCheese,

                                            vegetables = selectedVegetables.toList(),

                                            sauces = selectedSauces.toList(),

                                            seasonings = selectedSeasonings.toList(),

                                            toasted = toasted,

                                            footlong = footLong,

                                            notes = notes,

                                            creatorUid = user.uid,

                                            creatorUsername = user.username,

                                            createdAt = System.currentTimeMillis(),

                                            veg = isVEG,

                                            imageUrl = DEFAULT_RECIPE_IMAGE
                                        )


                                        RecipeRepository.uploadRecipe(
                                            recipe = recipe,
                                            onSuccess = { recipeId ->
                                                lastRecipe = recipeId
                                                posted = true;
                                                uploading = false
                                            },
                                            onFailure = {uploading = false}
                                        )

                                        return@getUser
                                    }

                                    else
                                    {
                                        ImageRepository.uploadImage(
                                            context = context,
                                            imageUri = imageUri!!,

                                            onSuccess = { imageUrl ->

                                                val recipe = Recipe(

                                                    recipeId = recipeId,

                                                    title = RecipeName,

                                                    bread = selectedBread,

                                                    protein = selectedProtein,

                                                    cheese = selectedCheese,

                                                    vegetables = selectedVegetables.toList(),

                                                    sauces = selectedSauces.toList(),

                                                    seasonings = selectedSeasonings.toList(),

                                                    toasted = toasted,

                                                    footlong = footLong,

                                                    notes = notes,

                                                    creatorUid = user.uid,

                                                    creatorUsername = user.username,

                                                    createdAt = System.currentTimeMillis(),

                                                    veg = isVEG,

                                                    imageUrl = imageUrl
                                                )
                                                RecipeRepository.uploadRecipe(
                                                    recipe = recipe,
                                                    onSuccess = { recipeId ->
                                                        lastRecipe = recipeId
                                                        posted = true;
                                                        uploading = false
                                                    },
                                                    onFailure = { uploading = false }
                                                )

                                            },

                                            onFailure = {

                                            }

                                        )
                                    }
                                },
                                onFailure = {}
                            )
                        },
                        modifier = Modifier
                            .height(70.dp)
                            .width(300.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.background
                        ),
                        shape = RoundedCornerShape(32.dp),
                        enabled = !uploading
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Upload,
                                contentDescription = "Upload",
                                tint = MaterialTheme.colorScheme.background,
                                modifier = Modifier.size(32.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "POST BLUEPRINT",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.background
                            )
                        }

                    }

                }
                AppMessageDialogBox(
                    show = posted,
                    title = "Blueprint Posted!",
                    message = "Your Blueprint has been posted",
                    onDismiss = {
                        posted = false; navController.navigate(
                        Routes.DetailScreen(
                            lastRecipe
                        )
                    )
                    },
                    confirmandDismiss = false,
                    onConfirm = {}
                )

            }

        }
    }


}