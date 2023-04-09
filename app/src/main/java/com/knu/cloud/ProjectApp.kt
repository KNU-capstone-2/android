package com.knu.cloud

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.knu.cloud.components.FABContent
import com.knu.cloud.navigation.*
import com.knu.cloud.screens.home.HomeScreen
import com.knu.cloud.screens.home.dashboard.HomeScreenPrev
import com.knu.cloud.screens.splash.ProjectSplashScreen
import com.knu.cloud.ui.theme.CloudTheme


@Composable
fun ProjectApp() {
    CloudTheme {
        HomeScreen()
    }
}


//        Surface(
//            color = MaterialTheme.colors.background,
//            modifier = Modifier
//                .fillMaxSize()
//        ) {
//            Column(
//                verticalArrangement = Arrangement.Top,
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                ProjectNavigation()
//            }
//        }
