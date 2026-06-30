package com.xyraveil.subshare.presentation.screen.welcomescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.xyraveil.subshare.R
import com.xyraveil.subshare.presentation.navigation.Routes


@Composable
fun WelcomeScreen(navController: NavController)
{
    Box( modifier = Modifier
        .fillMaxSize()
        .background(color = MaterialTheme.colorScheme.background))
    {
        Image(painter = painterResource(id = R.drawable.subwaysandwiches),contentDescription = "Welcome Image", contentScale = ContentScale.Crop)
        Column(modifier = Modifier
            .fillMaxSize()
            .padding( start = 20.dp,end = 20.dp,bottom = 80.dp), verticalArrangement = Arrangement.SpaceBetween, horizontalAlignment = Alignment.CenterHorizontally)
        {
            Spacer(modifier = Modifier.height(130.dp))
            Text(text = "Every \nSub Has\na Story.",
                color =  MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.ExtraBold,
                style = TextStyle(
                    shadow = Shadow(
                        color = MaterialTheme.colorScheme.background.copy(alpha = 0.5f),
                        offset = Offset(3f, 3f),
                        blurRadius = 8f
                    )
                ),
                fontSize = 90.sp,
                lineHeight = 90.sp,
                textAlign = TextAlign.Left,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                )

            Column()
            {
                Text(
                    text = "Browse thousands of community-made Subway combinations or create and share your signature order",
                    color = MaterialTheme.colorScheme.onSurface ,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(50.dp))
                Button(
                    onClick = { navController.navigate(Routes.LoginScreen) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "Sign in",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                }
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = { navController.navigate(Routes.RegisterScreen) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "Register",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                }
            }


        }
    }
}
