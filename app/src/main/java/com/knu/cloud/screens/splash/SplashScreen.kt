package com.knu.cloud.screens.splash

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.knu.cloud.components.CenterCircularProgressIndicator
import com.knu.cloud.navigation.ProjectScreens
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {
    LaunchedEffect(key1 = true) {
        delay(2000L)
        navController.navigate(ProjectScreens.LoginScreen.name)
    }
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(200.dp))
        Text(text = "SplashScreen")
        Spacer(modifier = Modifier.height(150.dp))
        CenterCircularProgressIndicator()
    }
}