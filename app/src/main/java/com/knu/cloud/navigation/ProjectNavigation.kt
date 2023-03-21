package com.knu.cloud.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.knu.cloud.screens.home.HomeScreen
import com.knu.cloud.screens.splash.SplashScreen

@Composable
fun ProjectNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = ProjectScreens.SplashScreen.name){
        composable(ProjectScreens.SplashScreen.name){
            SplashScreen(navController = navController)
        }
        composable(ProjectScreens.HomeScreen.name){
            HomeScreen(navController = navController)
        }
    }


}