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
import com.knu.cloud.navigation.MainDestination
import kotlinx.coroutines.delay

@Composable
fun ProjectSplashScreen(navController: NavController) {
    LaunchedEffect(key1 = true) {
        delay(2000L)
        navController.navigate(MainDestination.AUTH_ROUTE)
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(200.dp))
        Text(text = "SplashScreen")
        Spacer(modifier = Modifier.height(150.dp))
        CenterCircularProgressIndicator()
    }
}