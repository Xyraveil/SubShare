package com.xyraveil.subshare.presentation.ui_components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xyraveil.subshare.presentation.theme.lighteryellow
import com.xyraveil.subshare.presentation.theme.ligtheryellowdarkerscreen


@Composable

fun AppMessageDialogBox(
    show: Boolean,
    title: String,
    message: String,
    onDismiss: () -> Unit,
    confirmandDismiss: Boolean,
    onConfirm: () -> Unit,
    dismissMessage : String? = "No",
    confirmMessage : String? = "Yes"

) {
    if (show) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Black
                )
            },
            text = {
                Text(
                    text = message,
                    fontFamily = FontFamily.Monospace,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Black
                )
            },
            confirmButton = {
                Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), horizontalArrangement = Arrangement.SpaceBetween){
                    if (confirmandDismiss) TextButton(onClick = onDismiss) {
                        Text(
                            text = "No",
                            modifier = Modifier,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 18.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    } else {
                    }

                    if (confirmandDismiss) TextButton(onClick = onConfirm) {
                        Text(
                            text = "Yes",
                            modifier = Modifier,
                            fontFamily = FontFamily.Monospace,
                            fontSize = 18.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    } else {
                    }
                }

            }
            ,
            containerColor = if(isSystemInDarkTheme()) ligtheryellowdarkerscreen else lighteryellow

        )
    }
}