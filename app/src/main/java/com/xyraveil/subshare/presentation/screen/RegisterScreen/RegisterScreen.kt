package com.xyraveil.subshare.presentation.screen.RegisterScreen

import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.xyraveil.subshare.R
import com.xyraveil.subshare.data.repository.AuthRepository
import com.xyraveil.subshare.presentation.navigation.Routes
import com.xyraveil.subshare.presentation.ui_components.AppMessageDialogBox

@Composable

fun RegisterScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var Re_password by remember { mutableStateOf("") }
    var passwordLock by remember { mutableStateOf(true) }
    var errorMessage by remember {
        mutableStateOf<String?>(null)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)

    )
    {
        Image(
            painter = painterResource(R.drawable.subway_bg),
            contentDescription = "Banner",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Inside
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Register",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                fontSize = 36.sp,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(20.dp))
            //--------------------------------------------------------TextFields Start here
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("E-mail") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation =
                    if (passwordLock) PasswordVisualTransformation() else VisualTransformation.None,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.RemoveRedEye,
                        contentDescription = "ShowPassword",
                        tint = Color.Gray,
                        modifier = Modifier.clickable(onClick = { passwordLock = !passwordLock })
                    )
                }
            )
            OutlinedTextField(
                value = Re_password,
                onValueChange = { Re_password = it },
                label = { Text("Confirm Password") }
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        errorMessage = "Please enter a valid email address."
                        return@Button
                    }
                    if (username.isBlank()) return@Button

                    if (email.isBlank()) return@Button

                    if (password.length < 6) return@Button

                    if (password != Re_password) return@Button

                    AuthRepository.register(
                        username = username,
                        email = email,
                        password = password,

                        onSuccess = {
                            navController.navigate(Routes.HomeScreen) {
                                popUpTo(Routes.RegisterScreen) {
                                    inclusive = true
                                }
                            }
                        },

                        onFailure = { error ->
                            errorMessage = error
                        }
                    )
                },
                modifier = Modifier.padding(top = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,

                    ),
                shape = RoundedCornerShape(4.dp)
            )
            {
                Text(
                    text = "Register",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(modifier = Modifier, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "Already registered?",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Sign in",
                    style = MaterialTheme.typography.titleLarge.copy(textDecoration = TextDecoration.Underline),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.clickable(onClick = { navController.navigate(Routes.LoginScreen) })
                )
            }
            AppMessageDialogBox(
                show = errorMessage != null,
                title = "Registration Failed",
                message = errorMessage ?: "",
                onDismiss = {
                    errorMessage = null
                },
                confirmandDismiss = false,
                onConfirm = {}
            )

        }
    }
}
