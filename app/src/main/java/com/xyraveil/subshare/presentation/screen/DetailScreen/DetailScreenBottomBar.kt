package com.xyraveil.subshare.presentation.screen.DetailScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.xyraveil.subshare.domain.model.Recipe

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun DetailScreenBottomBar(recipe: Recipe) {

    var showCartDialog by remember { mutableStateOf(false) }
    var EmptyCartDialog by remember { mutableStateOf(false) }
    // var NewPrice by remember { mutableStateOf(product.price) }
    BottomAppBar(
        containerColor = Color.Transparent,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().height(100.dp)
            ) { }
//            Column(
//                modifier = Modifier
//                    .fillMaxHeight()
//                    .padding(vertical = 8.dp)
//            ) {
//                Text(
//                    text = "Price",
//                    fontSize = 16.sp
//                )
//                Spacer(modifier = Modifier.height(8.dp))
//                Text(
//                    text = "$${"%.2f".format(NewPrice)}",
//                    fontSize = 24.sp,
//                    fontWeight = FontWeight.SemiBold
//                )
//            }
//            Spacer(modifier = Modifier.width(70.dp))
//            Button(
//                onClick = {
//                    if(product.CartQuant==0) showCartDialog = true
//                    product.CartQuant++
//                },
//                modifier = Modifier
//                    .weight(1f)
//                    .height(55.dp),
//                shape = RoundedCornerShape(16.dp),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = MaterialTheme.colorScheme.primary,
//                    contentColor = IvoryWhite
//                )
//            ) {
//                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
//                    if (product.CartQuant > 0) Icon(
//                        imageVector = Icons.Default.Clear,
//                        contentDescription = "subtract",
//                        tint = Color.White,
//                        modifier = Modifier.clickable{product.CartQuant--}.weight(1f)
//
//                        )
//                    Text(
//                        text = if (product.CartQuant == 0) "Add to Cart" else "${product.CartQuant}",
//                        fontSize = 20.sp,
//                        fontWeight = FontWeight.SemiBold,
//                        modifier = Modifier.weight(1f),
//                        textAlign = TextAlign.Center,
//                        color = Color.White.copy(alpha = 0.8f)
//                    )
//                    if (product.CartQuant > 0) Icon(
//                        imageVector = Icons.Default.Delete,
//                        contentDescription = "Clear",
//                        tint = Color.White,
//                        modifier = Modifier.clickable{EmptyCartDialog = true}.weight(1f)
//
//                    )
//                }
//
//            }
//
//            AppMessageDialogBox(
//                show = showCartDialog,
//                title = "Added to Cart",
//                message = "Item has been added to your cart",
//                onDismiss = { showCartDialog = false },
//                confirmandDismiss = false,
//                onConfirm = {}
//            )
//            AppMessageDialogBox(
//                show = EmptyCartDialog,
//                title = "Empty Cart?",
//                message = "Are you sure you want to empty your cart?",
//                onDismiss = { EmptyCartDialog = false },
//                confirmandDismiss = true,
//                onConfirm = { product.CartQuant = 0 ; EmptyCartDialog = false ;}
//            )
        }
    }
}